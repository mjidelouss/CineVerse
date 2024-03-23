import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ReviewService} from "../../service/review.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {ProfileMovie} from "../../models/profile-movie";
import {Genre} from "../../models/genre";
import {MovieService} from "../../service/movie.service";
import {GenreService} from "../../service/genre.service";
@Component({
  selector: 'app-pofile-movies',
  templateUrl: './pofile-movies.component.html',
  styleUrls: ['./pofile-movies.component.scss']
})
export class PofileMoviesComponent implements OnInit, OnDestroy {

  AuthUserSub! : Subscription;
  movieId!: number;
  userId!: number;
  selectedDecade!: string;
  movies: ProfileMovie[] = [];
  genres: Genre[] = [];
  selectedGenre: string = '';
  constructor(private reviewService: ReviewService, private router: Router,
              private route: ActivatedRoute, private authService: AuthService,
              private movieService: MovieService, private genreService: GenreService) {}

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }
  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
    this.getReviewedMoviesWithRating()
    this.getGenres()
  }

  filterMoviesByGenre() {
    this.movieService.filterMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.movies = []
        for (const element of response.data) {
          const dbMovie = element;
          let movie: ProfileMovie = {
            image: "https://image.tmdb.org/t/p/w500/" + (dbMovie.movieImage || 'N/A'),
            id: dbMovie.movieId,
            rate: dbMovie.rating
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Movies By Genre:', error);
      }
    )
  }

  filterMovies() {
    // Filter movies by selected decade
    if (this.selectedDecade) {
      this.movieService.filterMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.movies = []
          for (const element of response.data) {
            const dbMovie = element;
            let movie: ProfileMovie = {
              image: "https://image.tmdb.org/t/p/w500/" + (dbMovie.movieImage || 'N/A'),
              id: dbMovie.movieId,
              rate: dbMovie.rating
            };
            this.movies.push(movie);
          }
        },
        (error) => {
          console.error('Error filtering movies by decade:', error);
        }
      );
    } else {
      this.getReviewedMoviesWithRating();
    }
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

  getReviewedMoviesWithRating() {
    this.reviewService.getReviewedMoviesWithRating(this.userId).subscribe(
      (response) => {
        this.movies = []
        for (const element of response.data) {
          const dbMovie = element;
          let movie: ProfileMovie = {
            image: "https://image.tmdb.org/t/p/w500/" + (dbMovie.movieImage || 'N/A'),
            id: dbMovie.movieId,
            rate: dbMovie.rating
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Trending Movies:', error);
      }
    );
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
