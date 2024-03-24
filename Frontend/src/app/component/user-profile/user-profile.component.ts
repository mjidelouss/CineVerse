import {Component, OnDestroy, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ReviewService} from "../../service/review.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnDestroy {
  userId!: number
  user!: any
  recentReviews: any[] = []
  likedMovies: TrendingMovie[] = [];
  watchCount!: number
  likeCount!: number
  loader = true;

  constructor(private authService: AuthService, private userService: UserService, private route: ActivatedRoute,
              private reviewService: ReviewService, private router: Router,) {
  }
  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
    this.getUser()
    this.getUserLikedMovies()
    this.getUserRecentReviews()
    this.getWatchCount()
    this.getLikeCount()
    setTimeout(() => {
      this.loader = false;
    }, 2000);
  }

  getUser() {
    this.userService.getUser(this.userId).subscribe(
      (response) => {
        this.user = response.data
      },
      (error) => {
        console.error('Error fetching User:', error);
      }
    )
  }

  getWatchCount() {
    this.reviewService.getUserWatchedCount(this.userId).subscribe(
      (response) => {
        this.watchCount = response.data
      },
      (error) => {
        console.error('Error fetching Watch Count:', error);
      }
    )
  }

  getLikeCount() {
    this.reviewService.getUserLikedCount(this.userId).subscribe(
      (response) => {
        this.likeCount = response.data
      },
      (error) => {
        console.error('Error fetching Like Count:', error);
      }
    )
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  getUserRecentReviews() {
    this.reviewService.getRecentUserReviews(this.userId).subscribe(
      (response) => {
        this.recentReviews = [];
        for (const element of response.data) {
          const dbReview = element;
          let review: any = {
            image: dbReview.movieImage || 'N/A',
            content: dbReview.content || 'N/A',
            rating: dbReview.rating || 'N/A',
            date: new Date(dbReview.timestamp).getFullYear(),
            timestamp: this.parseTimeStamp(dbReview.timestamp),
            movieTitle: dbReview.movieTitle,
            movieYear: dbReview.movieYear,
            movieImage: dbReview.movieImage,
            movieId: dbReview.movieId,
          };
          this.recentReviews.push(review);
        }
      },
      (error) => {
        console.error('Error fetching User Recent Reviews:', error);
      }
    );
  }

  getUserLikedMovies() {
    this.reviewService.getUserLikedMovies(this.userId).subscribe(
      (response) => {
        this.likedMovies = [];
        for (const element of response.data) {
          if (this.likedMovies.length < 5) {
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.movieId,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              language: dbMovie.movieLanguage || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
            };
            this.likedMovies.push(movie);
          } else {
            break;
          }
        }
      },
      (error) => {
        console.error('Error fetching Liked Movies:', error);
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

}
