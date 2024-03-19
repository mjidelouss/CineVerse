import {Component, OnDestroy, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {FormControl} from "@angular/forms";
import {User} from "../../models/user";

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit, OnDestroy {

  trendingMovies: TrendingMovie[] = [];
  movies: TrendingMovie[] = [];
  AuthUserSub! : Subscription;

  constructor(private authService : AuthService, private movieService: MovieService, private router: Router) {}

  ngOnInit() {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_MANAGER") {
            this.router.navigate(['dashboard']);
          }
        } else {
          this.router.navigate(['home']);
        }
      }
    })
    this.getTrendingMovies();
    this.getMovies()
  }
  getTrendingMovies() {
    this.movieService
      .getTrendingMovies()
      .subscribe(
        (response) => {
          this.trendingMovies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.id,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              director: dbMovie.director || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              overview: dbMovie.overview || 'N/A',
            };
            this.trendingMovies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Trending Movies:', error);
        }
      );
  }

  getMovies() {
    this.movieService
      .getLastMovies()
      .subscribe(
        (response) => {
          this.movies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.id,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              director: dbMovie.director || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              overview: dbMovie.overview || 'N/A',
            };
            this.movies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Movies:', error);
        }
      );
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  ngOnDestroy(): void {
  }

}
