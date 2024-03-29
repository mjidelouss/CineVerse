import {Component, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit{

  trendingMovies: TrendingMovie[] = [];
  movies: TrendingMovie[] = [];
  AuthUserSub! : Subscription;
  pageIndex: number = 1
  pageSize: number = 48
  totalMovies: number = 1000
  loader = true

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
    setTimeout(() => {
      this.loader = false;
    }, 2000);
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
              id: dbMovie.movieId,
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.movieYear || 'N/A',
              language: dbMovie.movieLanguage || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            };
            this.trendingMovies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Trending Movies:', error);
        }
      );
  }

  getMovies(): void {
    this.movieService.getMovies(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.movies = response.data.map((dbMovie: any) => ({
            id: dbMovie.movieId,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.movieYear || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            language: dbMovie.movieLanguage || 'N/A',
          }));
        },
        (error) => {
          console.error('Error fetching Movies:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getMovies();
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

}
