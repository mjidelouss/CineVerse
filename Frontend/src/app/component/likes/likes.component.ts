import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {ProfileMovie} from "../../models/profile-movie";
import {Genre} from "../../models/genre";
import {GenreService} from "../../service/genre.service";

@Component({
  selector: 'app-likes',
  templateUrl: './likes.component.html',
  styleUrls: ['./likes.component.scss']
})
export class LikesComponent implements OnInit, OnDestroy{

  userId!: number;
  movies: TrendingMovie[] = [];
  selectedDecade!: string;
  genres: Genre[] = [];
  selectedGenre: string = '';
  loader = true

  constructor(private route: ActivatedRoute, private router: Router, public dialog: MatDialog,
              private reviewService: ReviewService, private genreService: GenreService) {

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
    this.getUserLikedMovies()
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
  filterLikedMoviesByGenre() {
    this.reviewService.filterLikedMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.movies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: TrendingMovie = {
            id: dbMovie.movieId,
            title: dbMovie.title || 'N/A',
            year: dbMovie.year || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
            language: dbMovie.movieLanguage || 'N/A'
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Filtering Liked Movies By Genre:', error);
      }
    )
  }

  filterLikedMoviesByDecade() {
    if (this.selectedDecade) {
      this.reviewService.filterLikedMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.movies = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: TrendingMovie = {
              id: dbMovie.movieId,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              language: dbMovie.movieLanguage || 'N/A'
            };
            this.movies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Filtering Liked Movies By Decade:', error);
        }
      )
    } else {
      this.getUserLikedMovies()
    }
  }

  getUserLikedMovies() {
    this.reviewService.getUserLikedMovies(this.userId).subscribe(
      (response) => {
        this.movies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: TrendingMovie = {
            id: dbMovie.movieId,
            title: dbMovie.title || 'N/A',
            year: dbMovie.year || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
            language: dbMovie.movieLanguage || 'N/A'
          };
          this.movies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Liked Movies:', error);
      }
    )
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

  onLikesClick(): void {
    this.router.navigate(['/likes', this.userId]);
  }

  onReviewsClick(): void {
    this.router.navigate(['/profile-reviews', this.userId]);
  }
  onWatchListClick(): void {
    this.router.navigate(['/watchlist', this.userId]);
  }

}
