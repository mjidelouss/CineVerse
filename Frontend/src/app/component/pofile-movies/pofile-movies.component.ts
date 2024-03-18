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
        console.log(response)
        this.movies = response.map((element: any[]) => {
          const dbMovie = element;
          return {
            id: dbMovie[0].movieId,
            title: dbMovie[0].title || 'N/A',
            year: dbMovie[0].year || 'N/A',
            director: dbMovie[0].director || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + (dbMovie[0].image || 'N/A'),
            overview: dbMovie[0].overview || 'N/A',
            rate: dbMovie[1]
          };
        });
      },
      (error) => {
        console.error('Error fetching Movies By Genre:', error);
      }
    )
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
        this.movies = response.data.map((element: any[]) => {
          const dbMovie = element;
          return {
            id: dbMovie[0].movieId,
            title: dbMovie[0].title || 'N/A',
            year: dbMovie[0].year || 'N/A',
            director: dbMovie[0].director || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + (dbMovie[0].image || 'N/A'),
            overview: dbMovie[0].overview || 'N/A',
            rate: dbMovie[1]
          };
        });
      },
      (error) => {
        console.error('Error fetching Trending Movies:', error);
      }
    );
  }

}
