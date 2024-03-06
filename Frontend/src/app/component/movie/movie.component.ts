import {Component, OnInit} from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Movie} from "../../models/movie";
import {MovieCredits} from "../../models/movie-credits";
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  movieId!: number;
  movieCredits!: MovieCredits;
  movie!: Movie

  constructor(private sanitizer: DomSanitizer, private movieService: MovieService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.movieId = +params['id'];
    });
    this.getMovie(this.movieId);
  }
  getMovie(movieId: number) {
    this.movieService
      .getMovieById(movieId)
      .subscribe(
        (response: any) => {
          this.movie = response.data;
          this.movieCredits = this.parseMovieData(this.movie.movieData);
          console.log(this.movieCredits)
        },
        (error) => {
          console.error("Error fetching Movie Details & Credits:", error);
        }
      );
  }

  getSanitizedBackground(): SafeStyle {
    const backgroundImage = `url('https://image.tmdb.org/t/p/original${this.movie.movie_background}')`;
    return this.sanitizer.bypassSecurityTrustStyle(backgroundImage);
  }

  // Function to deserialize movieData string into MovieData model
  parseMovieData(movieData: string): MovieCredits {
    try {
      return JSON.parse(movieData) as MovieCredits;
    } catch (error) {
      console.error('Error parsing movieData:', error);
      return {} as MovieCredits;
    }
  }

}
