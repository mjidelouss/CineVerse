import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";
import {Diary} from "../../models/diary";
import {Genre} from "../../models/genre";
import {GenreService} from "../../service/genre.service";
import {RecentReview} from "../../models/recent-review";
import {ModalDismissReasons, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {Review} from "../../models/review";

@Component({
  selector: 'app-diary',
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.scss']
})
export class DiaryComponent implements OnInit, OnDestroy{

  private modalService = inject(NgbModal);
  userId!: number;
  closeResult = '';
  selectedMovie: any
  diaryMovies: Diary[] = [];
  selectedDecade!: string;
  review: Review = { userId: {} as number, movieId: {} as number, content: '' };
  genres: Genre[] = [];
  selectedGenre: string = '';
  private modalRef: NgbModalRef | undefined;
  constructor(private route: ActivatedRoute,private router: Router, private genreService: GenreService
              ,public dialog: MatDialog, private reviewService: ReviewService) {

  }
  ngOnDestroy(): void {
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
    this.getMyReviews()
    this.getGenres()
  }

  getGenres() {
    this.genreService.getGenres().subscribe(
      (response) => {
        this.genres = response.data
      },
      (error) => {
        console.error('Error fetching Genres:', error);
      }
    )
  }
  getMyReviews() {
    this.reviewService.getUserReviews(this.userId).subscribe(
      (response) => {
        this.diaryMovies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: Diary = {
            reviewId: dbMovie.reviewId,
            id: dbMovie.movieId,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.year || 'N/A',
            content: dbMovie.content || 'N/A',
            language: dbMovie.movieLanguage || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            like: dbMovie.liked,
            rating: dbMovie.rating,
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.diaryMovies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Diary Movies:', error);
      }
    )
  }

  filterReviewdMoviesByGenre() {
    this.reviewService.filterDiaryMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.diaryMovies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: Diary = {
            reviewId: dbMovie.reviewId,
            id: dbMovie.movieId,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.year || 'N/A',
            content: dbMovie.content || 'N/A',
            language: dbMovie.movieLanguage || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            like: dbMovie.liked,
            rating: dbMovie.rating,
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.diaryMovies.push(movie);
        }
      },
      (error) => {
        console.error('Error filtering Diary Movies By Genre:', error);
      }
      )
  }

  filterReviewdMoviesByDecade() {
    if (this.selectedDecade) {
      this.reviewService.filterDiaryMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.diaryMovies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: Diary = {
              reviewId: dbMovie.reviewId,
              id: dbMovie.movieId,
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.year || 'N/A',
              content: dbMovie.content || 'N/A',
              language: dbMovie.movieLanguage || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
              like: dbMovie.liked,
              rating: dbMovie.rating,
              timestamp: this.parseTimeStamp(dbMovie.timestamp)
            };
            this.diaryMovies.push(movie);
          }
        },
        (error) => {
          console.error('Error filtering Diary Movies By Decade:', error);
        }
      )
    } else {
      this.getMyReviews()
    }

  }
  openReview(content: any, movie: any): void {
    this.selectedMovie = movie;
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
  updateReview(reviewId: number) {
    this.review.movieId = this.selectedMovie.id
    this.review.userId = this.userId
    this.review.content = this.selectedMovie.content
    this.reviewService.updateReview(this.review, reviewId).subscribe(
      (response: any) => {
        const existingReviewIndex = this.diaryMovies.findIndex(movie => movie.reviewId === reviewId);
        if (existingReviewIndex !== -1) {
          this.diaryMovies[existingReviewIndex].content = response.data.content;
        } else {
          console.error("Review with reviewId", reviewId, "not found in diaryMovies array.");
        }
      },
      (error) => {
        console.error("Error updating movie review:", error);
      }
    );
    this.closeReviewModal();
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

  parseTimeStamp(timestamp: string):{ day: string, month: string, dayOfMonth: string}  {
    let parsedTimeStamp = { day: '', month: '', dayOfMonth: ''};
    const date = new Date(timestamp);
    parsedTimeStamp.day = this.getDayOfWeek(date.getDay());
    parsedTimeStamp.month = new Intl.DateTimeFormat('en-US', { month: 'long' }).format(date);
    parsedTimeStamp.dayOfMonth = date.getDate().toString();
    return parsedTimeStamp
  }

  private getDayOfWeek(dayIndex: number): string {
    const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return daysOfWeek[dayIndex];
  }

  onProfileClick(): void {
    this.router.navigate(['/profile', this.userId]);
  }

  onProfileMoviesClick(): void {
    this.router.navigate(['/profile-movies', this.userId]);
  }

  onDiaryClick(): void {
    this.router.navigate(['/diary', this.userId]);
  }

  onLikesClick(): void {
    this.router.navigate(['/likes', this.userId]);
  }

  onReviewsClick(): void {
    this.router.navigate(['/profile-reviews', this.userId]);
  }
  onWatchListClick(): void {
    this.router.navigate(['/watchlist', this.userId]);
  }

}
