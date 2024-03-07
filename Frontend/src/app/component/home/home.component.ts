import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MovieService} from "../../service/movie.service";
import {Movie} from "../../models/movie";
import {TrendingMovie} from "../../models/trendingMovie";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  trendingMovies: TrendingMovie[] = [];

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit() {
    this.getTrendingMovies();
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
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
              id: dbMovie.id,
              title: dbMovie.title || 'N/A',
              year: dbMovie.year || 'N/A',
              director: dbMovie.director || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.image || 'N/A',
              overview: dbMovie.overview || 'N/A',
            };
            this.trendingMovies.push(movie);
          }
        },
        (error) => {
          console.error('Error fetching Trending Movies:', error);
        }
      );
  }


  viewMovie(competition: any) {
    this.router.navigate(['/movie-details'], { state: { competition } });
  }
}
