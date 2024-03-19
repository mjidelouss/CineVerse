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

  filterWatchedMoviesByGenre() {
    this.watchlistService.filterWatchedMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        console.log(response)
        this.movies = [];
        for (const element of response) {
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
        console.error('Error fetching Filtering WatchList Movies By Genre:', error);
      }
    )
  }

  filterWatchedMoviesByDecade() {
    if (this.selectedDecade) {
      this.watchlistService.filterWatchedMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.movies = [];
          for (const element of response) {
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
          console.error('Error fetching Filtering WacthList Movies By Decade:', error);
        }
      )
    } else {
      this.getUserWatchList()
    }

  }

}
