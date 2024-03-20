import {Component, OnDestroy, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {FormControl} from "@angular/forms";
import {User} from "../../models/user";
import {ReviewService} from "../../service/review.service";

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit, OnDestroy {

  trendingMovies: TrendingMovie[] = [];
  movies: TrendingMovie[] = [];
  reviews: any[] = []
  AuthUserSub! : Subscription;

  constructor(private authService : AuthService, private movieService: MovieService,
              private router: Router, private reviewService: ReviewService) {}

  ngOnInit() {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['dashboard']);
          } else if (user.role.name == "ROLE_USER") {
            this.router.navigate(['user-home']);
          }
        } else {
          this.router.navigate(['']);
        }
      }
    })
    this.getTrendingMovies();
    this.getMovies()
    this.getPopularReviews()
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

  getPopularReviews() {
    this.reviewService.getPopularReviews().subscribe(
      (response) => {
        console.log(response.data)
        this.reviews = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: any = {
            id: dbMovie.movie.id,
            name: dbMovie.appUser.firstname + ' ' + dbMovie.appUser.lastname,
            title: dbMovie.movie.title || 'N/A',
            year: dbMovie.movie.year || 'N/A',
            director: dbMovie.movie.director || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movie.image || 'N/A',
            like: dbMovie.liked,
            rate: dbMovie.rating,
            content: dbMovie.content,
            date: new Date(dbMovie.timestamp).getFullYear(),
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.reviews.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Popular Reviews:', error);
      }
    )
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
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

  ngOnDestroy(): void {
  }

}
