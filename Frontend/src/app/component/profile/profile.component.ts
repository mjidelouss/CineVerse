import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RecentReview} from "../../models/recent-review";
import {ReviewService} from "../../service/review.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {ProfileReview} from "../../models/profile-review";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  userId!: number
  user!: any
  recentReviews: ProfileReview[] = []
  likedMovies: TrendingMovie[] = [];

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

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  onSettingsClick(): void {
    this.router.navigate(['/settings', this.userId]);
  }
  onProfileClick(): void {
    this.router.navigate(['/profile', this.userId]);
  }

  onReviewsClick(): void {
    this.router.navigate(['/profile-reviews', this.userId]);
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

  onWatchListClick(): void {
    this.router.navigate(['/watchlist', this.userId]);
  }

  getUserRecentReviews() {
    this.reviewService.getRecentUserReviews(this.userId).subscribe(
      (response) => {
        console.log(response.data)
        this.recentReviews = [];
        for (const element of response.data) {
          const dbReview = element;
          let review: ProfileReview = {
            firstname: dbReview.appUser.firstname || 'N/A',
            lastname: dbReview.appUser.lastname || 'N/A',
            image: dbReview.appUser.image || 'N/A',
            content: dbReview.content || 'N/A',
            rating: dbReview.rating || 'N/A',
            likes: dbReview.likes,
            timestamp: dbReview.timestamp|| 'N/A',
            movie: dbReview.movie
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
              id: dbMovie.id,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              director: dbMovie.director || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              overview: dbMovie.overview || 'N/A',
            };
            this.likedMovies.push(movie);
          } else {
            break;  // Exit the loop if 5 movies have been added
          }
        }
      },
      (error) => {
        console.error('Error fetching Liked Movies:', error);
      }
    );
  }


}
