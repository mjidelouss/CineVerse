import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../../service/auth.service";
import {WatchlistService} from "../../service/watchlist.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {Genre} from "../../models/genre";
import {GenreService} from "../../service/genre.service";

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit, OnDestroy{

  userId!: number;
  selectedDecade!: string;
  genres: Genre[] = [];
  selectedGenre: string = '';
  movies: TrendingMovie[] = [];
  loader = true;

  constructor(private route: ActivatedRoute,private router: Router, public dialog: MatDialog,
              private authService: AuthService, private watchlistService: WatchlistService, private genreService: GenreService) {

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
    this.getGenres()
    setTimeout(() => {
      this.loader = false;
    }, 2000);
  }

  getGenres() {
    this.genreService.getGenres().subscribe(
      (response) => {
        this.genres = response.data
      },
      (error) => {
        console.error('Error fetching Genres:', error);
      }
    )
  }

  getUserWatchList() {
    this.watchlistService.getUserWatchlist(this.userId).subscribe(
      (response) => {
        this.movies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: TrendingMovie = {
            id: dbMovie.movieId,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.movieYear || 'N/A',
            language: dbMovie.movieLanguage || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching WatchList Movies:', error);
      }
    )
  }

  filterWatchedMoviesByGenre() {
    this.watchlistService.filterWatchedMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.movies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: TrendingMovie = {
            id: dbMovie.movieId,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.movieYear || 'N/A',
            language: dbMovie.movieLanguage || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Filtering WatchList Movies By Genre:', error);
      }
    )
  }

  filterWatchedMoviesByDecade() {
    if (this.selectedDecade) {
      this.watchlistService.filterWatchedMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.movies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.movieId,
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.movieYear || 'N/A',
              language: dbMovie.movieLanguage || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            };
            this.movies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Filtering WacthList Movies By Decade:', error);
        }
      )
    } else {
      this.getUserWatchList()
    }
  }

  onProfileClick(): void {
    this.router.navigate(['/profile', this.userId]);
  }

  onProfileMoviesClick(): void {
    this.router.navigate(['/profile-movies', this.userId]);
  }

  onDiaryClick(): void {
    this.router.navigate(['/diary', this.userId]);
  }

  onReviewsClick(): void {
    this.router.navigate(['/profile-reviews', this.userId]);
  }

  onLikesClick(): void {
    this.router.navigate(['/likes', this.userId]);
  }

  onWatchListClick(): void {
    this.router.navigate(['/watchlist', this.userId]);
  }

}
