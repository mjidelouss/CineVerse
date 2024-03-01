import {Component, OnInit} from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Movie} from "../../models/movie";
import {MovieDetails} from "../../models/movie-details";
import {MovieCredits} from "../../models/movie-credits";
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.scss']
})
export class MovieComponent implements OnInit {

  movieId!: number;
  movieDetails!: MovieDetails;
  movieCredits!: MovieCredits;

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
          this.movieCredits = response.data.movieCredits;
          this.movieDetails = response.data.movieDetailsTrailer;
        },
        (error) => {
          console.error("Error fetching Movie Details & Credits:", error);
        }
      );
  }

  getSanitizedBackground(): SafeStyle {
    const backgroundImage = `url('https://image.tmdb.org/t/p/original${this.movieDetails.movie_background}')`;
    return this.sanitizer.bypassSecurityTrustStyle(backgroundImage);
  }

}
