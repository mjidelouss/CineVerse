import {Component, OnDestroy, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {ReviewService} from "../../service/review.service";

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit, OnDestroy {

  trendingMovies: any[] = [];
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
              id: dbMovie.movieId,
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.movieYear || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
              language: dbMovie.movieLanguage || 'N/A'
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
            console.log("Last Movies :")
            console.log(response.data)
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.movieId,
              language: dbMovie.movieLanguage || 'N/A',
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.movieYear || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
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
        this.reviews = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: any = {
            id: dbMovie.movieId,
            userId: dbMovie.userId,
            userImage: dbMovie.userImage,
            name: dbMovie.userFirstname + ' ' + dbMovie.userLastname,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.movieYear || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
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

  onUserProfileClick(userId: number): void {
    this.router.navigate(['/user-profile', userId]);
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
