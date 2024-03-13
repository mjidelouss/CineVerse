import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {Diary} from "../../models/diary";
import {MovieCredits} from "../../models/movie-credits";

@Component({
  selector: 'app-diary',
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.scss']
})
export class DiaryComponent implements OnInit, OnDestroy{

  userId!: number;
  diaryMovies: Diary[] = [];
  constructor(private route: ActivatedRoute,private router: Router, public dialog: MatDialog, private reviewService: ReviewService) {

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
    this.getMyReviews()
  }

  getMyReviews() {
    this.reviewService.getUserReviews(this.userId).subscribe(
      (response) => {
        console.log(response.data)
        this.diaryMovies = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: Diary = {
            id: dbMovie.movie.id,
            title: dbMovie.movie.title || 'N/A',
            year: dbMovie.movie.year || 'N/A',
            director: dbMovie.movie.director || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movie.image || 'N/A',
            like: dbMovie.liked,
            rate: dbMovie.rating,
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.diaryMovies.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Diary Movies:', error);
      }
    )
  }

  parseTimeStamp(timestamp: string):{ day: string, month: string, dayOfMonth: string}  {
    let parsedTimeStamp = { day: '', month: '', dayOfMonth: ''};
    const date = new Date(timestamp);
    parsedTimeStamp.day = this.getDayOfWeek(date.getDay());
    parsedTimeStamp.month = new Intl.DateTimeFormat('en-US', { month: 'long' }).format(date);
    parsedTimeStamp.dayOfMonth = date.getDate().toString();
    return parsedTimeStamp
  }

  private getDayOfWeek(dayIndex: number): string {
    const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return daysOfWeek[dayIndex];
  }

}
