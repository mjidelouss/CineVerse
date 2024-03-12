import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ReviewService} from "../../service/review.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {ProfileMovie} from "../../models/profile-movie";

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
  constructor(private reviewService: ReviewService, private router: Router,
              private route: ActivatedRoute, private authService: AuthService) {}

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }
  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.movieId = +params['id'];
    });
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.userId = user.id;
        }
      }
    });
    this.getReviewedMoviesWithRating()
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
