import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MovieService} from "../../service/movie.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ReviewService} from "../../service/review.service";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  trendingMovies: TrendingMovie[] = [];
  AuthUserSub! : Subscription;
  searchResults: any[] = [];
  reviews: any[] = []
  searchTermControl: FormControl = new FormControl();

  constructor(private movieService: MovieService, private router: Router, private reviewService: ReviewService,
              private authService : AuthService, private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_MANAGER") {
            this.router.navigate(['dashboard']);
          } else if (user.role.name == "ROLE_USER") {
            this.router.navigate(['user-home']);
          }
        }
      }
    })
    this.searchTermControl.valueChanges.pipe(
      debounceTime(300) // Adjust the debounce time as needed (e.g., 300 milliseconds)
    ).subscribe(() => {
      this.onSearch();
    });
    this.getTrendingMovies();
    this.getPopularReviews()
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
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

  onSearch(): void {
    const searchTerm = this.searchTermControl.value.trim();
    if (searchTerm == "") {
      this.searchResults = [];
    } else{
      this.movieService.searchMovies(searchTerm).subscribe(
        (response) => {
          this.searchResults = response.data;
        },
        (error) => {
          console.error('Error fetching movie search results:', error);
        }
      );
    }
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
  onUserProfileClick(userId: number): void {
    this.router.navigate(['/user-profile', userId]);
  }

}
