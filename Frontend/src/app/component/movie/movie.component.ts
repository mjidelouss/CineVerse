import {Component, inject, OnInit, TemplateRef} from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Movie} from "../../models/movie";
import {MovieCredits} from "../../models/movie-credits";
import {DomSanitizer, SafeResourceUrl, SafeStyle} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {SimilarMovie} from "../../models/similarMovie";
import {TrendingMovie} from "../../models/trendingMovie";

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  private modalService = inject(NgbModal);
  closeResult = '';
  movieId!: number;
  movieCredits!: MovieCredits;
  movie!: Movie
  similarMovies: SimilarMovie[] = [];
  hoverStar: number = 0;
  selectedStar: number = 0;
  hoveredIcon: string | null = null;

  constructor(private router: Router, public dialog: MatDialog, private sanitizer: DomSanitizer, private movieService: MovieService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.movieId = +params['id'];
    });
    this.getMovie(this.movieId);
    this.getSimilarMovies(this.movieId)
  }

  resetStars() {
    this.hoverStar = this.selectedStar;
  }

  rateMovie(star: number) {
    if (this.selectedStar === star) {
      // If already selected, unselect
      this.selectedStar = 0;
    } else {
      this.selectedStar = star;
      // TODO: Send the selected star rating to the backend
    }
  }

  onIconHover(icon: string): void {
    this.hoveredIcon = icon;
  }

  onIconClick(icon: string): void {
    // Send a message to the backend based on the clicked icon
    console.log(`Icon clicked: ${icon}`);
  }
  getMovie(movieId: number) {
    this.movieService
      .getMovieById(movieId)
      .subscribe(
        (response: any) => {
          this.movie = response.data;
          this.movieCredits = this.parseMovieData(this.movie.movieData);
        },
        (error) => {
          console.error("Error fetching Movie Details & Credits:", error);
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
}
