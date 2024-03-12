import {Component, inject, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Movie} from "../../models/movie";
import {MovieCredits} from "../../models/movie-credits";
import {DomSanitizer, SafeResourceUrl, SafeStyle} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {ModalDismissReasons, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {SimilarMovie} from "../../models/similarMovie";
import {ReviewService} from "../../service/review.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {Rate} from "../../models/rate";
import {Review} from "../../models/review";
import {RecentReview} from "../../models/recent-review";
import {WatchlistService} from "../../service/watchlist.service";

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit, OnDestroy {

  private modalService = inject(NgbModal);
  AuthUserSub! : Subscription;
  closeResult = '';
  movieId!: number;
  movieCredits!: MovieCredits;
  movie!: Movie
  similarMovies: SimilarMovie[] = [];
  recentReviews: RecentReview[] = [];
  hoverStar: number = 0;
  selectedStar: number = 0;
  clickedIcon: string | null = null;
  watched!: boolean
  watchlist!: boolean
  liked!: boolean
  rate: Rate = { userId: {} as number, movieId: {} as number };
  review: Review = { userId: {} as number, movieId: {} as number, content: '' };
  showAllCast: boolean = false;
  selectedTab: 'cast' | 'crew' | 'genre' | 'details' = 'cast';
  private modalRef: NgbModalRef | undefined;
  showAllContent: { cast: boolean; crew: boolean; genre: boolean; details: boolean } = {
    cast: false,
    crew: false,
    genre: false,
    details: false
  };
  constructor(private router: Router, public dialog: MatDialog,
              private sanitizer: DomSanitizer, private movieService: MovieService,
              private route: ActivatedRoute, private reviewService: ReviewService,
              private authService: AuthService, private watchlistService: WatchlistService) {

  }
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.movieId = +params['id'];
    });
    this.getMovie(this.movieId);
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.rate.userId = user.id;
          this.review.userId = user.id;
        }
      }
    });
    this.getReview(this.movieId);
    this.getRecentReviews(this.movieId);
    this.getSimilarMovies(this.movieId);
  }



  resetStars() {
    this.hoverStar = this.selectedStar;
  }

  saveReview() {
    this.reviewService.addReview(this.review).subscribe(
      (response: any) => {
        console.log(response.data)
      },
      (error) => {
        console.error("Error Adding Movie Review:", error);
      }
    );
    this.closeReviewModal();
  }

  rateMovie(star: number) {
    if (this.selectedStar === star) {
      // If already selected, unselect
      this.selectedStar = 0;
    } else {
      this.selectedStar = star;
      this.watched = true;
    }
    this.reviewService.addRating(this.rate, star).subscribe(
      (response: any) => {
      },
      (error) => {
        console.error("Error Rating Movie:", error);
      }
    );
  }

  addMovieToWatchList(watch: boolean) {
    this.reviewService.saveWatchList(this.rate, watch).subscribe(
      (response: any) => {
        this.watchlist = response.data
      },
      (error) => {
        console.error("Error WatchList:", error);
      }
    )
    if (watch) {
      this.watchlistService.addMovieToWatchList(this.rate).subscribe(
        (response: any) => {
        },
        (error) => {
          console.error("Error Adding Movie to WatchList:", error);
        }
      )
    } else {
      this.watchlistService.removeMovieFromWatchList(this.rate).subscribe(
        (response: any) => {
        },
        (error) => {
          console.error("Error Removing Movie from WatchList:", error);
        }
      )
    }
  }

  likeMovie(like: boolean) {
    this.reviewService.saveLike(this.rate, like).subscribe(
      (response: any) => {
        this.liked = response.data
      },
      (error) => {
        console.error("Error Liking Movie:", error);
      }
    );
  }

  onIconClick(icon: string): void {
    this.clickedIcon = this.clickedIcon === icon ? null : icon;
    if (icon === "visibility") {
      this.watched = true;
    }
    if (icon == "watch_later") {
      if (this.watchlist == true) {
        this.addMovieToWatchList(false)
        this.watchlist = false
      } else {
        this.addMovieToWatchList(true)
        this.watchlist = true
      }
    }
    if (icon === "favorite") {
      if (this.liked == true) {
        this.likeMovie(false)
        this.liked = false;
      } else {
        this.likeMovie(true)
        this.liked = true;
      }

    }
  }

  get displayedCast(): string[] {
    return this.showAllCast ? this.movieCredits.cast : this.movieCredits.cast.slice(0, 25);
  }

  toggleShowAllCast(): void {
    this.showAllCast = !this.showAllCast;
  }
  get displayedContent(): { cast: string[]; crew: { [key: string]: any }; genre: string[];} {
    switch (this.selectedTab) {
      case 'cast':
        return { cast: this.showAllContent.cast ? this.movieCredits.cast : this.movieCredits.cast.slice(0, 3), crew: {}, genre: [] };
      case 'crew':
        // Include all crew members
        const crewMembers = Object.entries(this.movieCredits)
          .filter(([key, value]) => key !== 'cast' && Array.isArray(value))
          .reduce((acc, [key, value]) => {
            acc[key] = Array.isArray(value) ? value : [value];
            return acc;
          }, {} as { [key: string]: string[] });
        return { cast: [], crew: this.showAllContent.crew ? crewMembers : crewMembers, genre: []};
      case 'genre':
        return { cast: [], crew: {}, genre: this.showAllContent.genre ? this.movie.genres : this.movie.genres};
      case 'details':
        return { cast: [], crew: {}, genre: []};
      default:
        return { cast: [], crew: {}, genre: []};
    }
  }

  selectTab(tab: 'cast' | 'crew' | 'genre' | 'details'): void {
    this.selectedTab = tab;
    this.resetShowAllContent();
  }

  toggleShowAllContent(tab: 'cast' | 'crew' | 'genre' | 'details'): void {
    this.showAllContent[tab] = !this.showAllContent[tab];
  }

  resetShowAllContent(): void {
    this.showAllContent = {
      cast: false,
      crew: false,
      genre: false,
      details: false
    };
  }
  getMovie(movieId: number) {
    this.movieService
      .getMovieById(movieId)
      .subscribe(
        (response: any) => {
          this.movie = response.data;
          this.movieCredits = this.parseMovieData(this.movie.movieData);
          this.rate.movieId = this.movie.id;
          this.review.movieId = this.movie.id;
        },
        (error) => {
          console.error("Error fetching Movie Details & Credits:", error);
        }
      );
  }

  getReview(movieId: number) {
    this.reviewService.getReview(movieId, this.rate.userId).subscribe(
      (response: any) => {
        this.watched = response.data.watched;
        this.selectedStar = response.data.rating;
        this.liked = response.data.liked;
      },
      (error) => {
        console.error("Error fetching Movie Details & Credits:", error);
      }
    );
  }

  getRecentReviews(movieId: number) {
    this.reviewService.getRecentReviews(movieId).subscribe(
      (response) => {
        console.log(response.data)
        this.recentReviews = [];
        for (const element of response.data) {
          const dbReview = element;
          let review: RecentReview = {
            firstname: dbReview.appUser.firstname || 'N/A',
            lastname: dbReview.appUser.lastname || 'N/A',
            image: dbReview.appUser.image || 'N/A',
            content: dbReview.content || 'N/A',
            rating: dbReview.rating || 'N/A',
            likes: dbReview.likes,
            timestamp: dbReview.timestamp|| 'N/A'
          };
          console.log(review)
          this.recentReviews.push(review);
        }
      },
      (error) => {
        console.error('Error fetching Recent Reviews:', error);
      }
    );
  }

  getSimilarMovies(movieId: number) {
    this.movieService
      .getSimilarMovies(movieId)
      .subscribe(
        (response) => {
          this.similarMovies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: SimilarMovie = {
              id: dbMovie.id,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              director: dbMovie.director || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              overview: dbMovie.overview || 'N/A',
            };
            this.similarMovies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Trending Movies:', error);
        }
      );
  }

  getSanitizedBackground(): SafeStyle {
    const backgroundImage = `url('https://image.tmdb.org/t/p/original${this.movie.movie_background}')`;
    return this.sanitizer.bypassSecurityTrustStyle(backgroundImage);
  }
  getSanitizedTrailer(): SafeResourceUrl  {
    const trailer = `https://www.youtube.com/embed/${this.movie.trailer}`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(trailer);
  }

  // Function to deserialize movieData string into MovieData model
  parseMovieData(movieData: string): MovieCredits {
    try {
      return JSON.parse(movieData) as MovieCredits;
    } catch (error) {
      console.error('Error parsing movieData:', error);
      return {} as MovieCredits;
    }
  }
  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }
  open(content: TemplateRef<any>) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  openReview(content: any): void {
    this.modalRef = this.modalService.open(content, { ariaLabelledBy: 'modal-review' });

    this.modalRef.result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }
  closeReviewModal(): void {
    if (this.modalRef) {
      this.modalRef.close();
    }
  }
  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  ngOnDestroy() {
    if (this.AuthUserSub) {
      this.AuthUserSub.unsubscribe();
    }
  }

  protected readonly Object = Object;
}
