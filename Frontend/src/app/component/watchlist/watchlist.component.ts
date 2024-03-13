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

  userId!: number;
  movies: TrendingMovie[] = [];

  constructor(private route: ActivatedRoute,private router: Router, public dialog: MatDialog,
              private authService: AuthService, private watchlistService: WatchlistService) {

  }
  ngOnDestroy(): void {
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
    this.getUserWatchList()
  }

  getUserWatchList() {
    this.watchlistService.getUserWatchlist(this.userId).subscribe(
      (response) => {
        console.log(response.data)
        this.movies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: TrendingMovie = {
            id: dbMovie.movie.id,
            title: dbMovie.movie.title || 'N/A',
            year: dbMovie.movie.year || 'N/A',
            director: dbMovie.movie.director || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movie.image || 'N/A',
            overview: dbMovie.movie.overview || 'N/A',
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
