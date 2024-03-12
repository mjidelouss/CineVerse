import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DomSanitizer} from "@angular/platform-browser";
import {MovieService} from "../../service/movie.service";
import {ReviewService} from "../../service/review.service";
import {AuthService} from "../../service/auth.service";
import {WatchlistService} from "../../service/watchlist.service";
import {TrendingMovie} from "../../models/trendingMovie";

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit, OnDestroy{

  AuthUserSub! : Subscription;
  userId!: number;
  movies: TrendingMovie[] = [];

  constructor(private router: Router, public dialog: MatDialog, private authService: AuthService,
              private watchlistService: WatchlistService) {

  }
  ngOnDestroy(): void {
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.userId = user.id;
        }
      }
    });

  }

  getUserWatchList() {
    this.watchlistService.getUserWatchlist(this.userId).subscribe(
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
        console.error('Error fetching WatchList Movies:', error);
      }
    )
  }

}
