import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";
import {TrendingMovie} from "../../models/trendingMovie";

@Component({
  selector: 'app-likes',
  templateUrl: './likes.component.html',
  styleUrls: ['./likes.component.scss']
})
export class LikesComponent implements OnInit, OnDestroy{

  userId!: number;
  movies: TrendingMovie[] = [];

  constructor(private route: ActivatedRoute, private router: Router, public dialog: MatDialog,
              private reviewService: ReviewService) {

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
  }

  getUserLikedMovies() {
    this.reviewService.getUserLikedMovies(this.userId).subscribe(
      (response) => {
        console.log(response.data)
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
        console.error('Error fetching Liked Movies:', error);
      }
    )
  }

}
