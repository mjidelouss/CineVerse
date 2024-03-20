import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";
import {Diary} from "../../models/diary";
import {Genre} from "../../models/genre";
import {GenreService} from "../../service/genre.service";

@Component({
  selector: 'app-diary',
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.scss']
})
export class DiaryComponent implements OnInit, OnDestroy{

  userId!: number;
  diaryMovies: Diary[] = [];
  selectedDecade!: string;
  genres: Genre[] = [];
  selectedGenre: string = '';
  constructor(private route: ActivatedRoute,private router: Router, private genreService: GenreService
              ,public dialog: MatDialog, private reviewService: ReviewService) {

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
  getMyReviews() {
    this.reviewService.getUserReviews(this.userId).subscribe(
      (response) => {
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

  filterReviewdMoviesByGenre() {
    this.reviewService.filterDiaryMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.diaryMovies = [];
        for (const element of response) {
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
        console.error('Error filtering Diary Movies By Genre:', error);
      }
      )
  }

  filterReviewdMoviesByDecade() {
    if (this.selectedDecade) {
      this.reviewService.filterDiaryMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.diaryMovies = [];
          for (const element of response) {
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
          console.error('Error filtering Diary Movies By Decade:', error);
        }
      )
    } else {
      this.getMyReviews()
    }

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

  onWatchListClick(): void {
    this.router.navigate(['/watchlist', this.userId]);
  }

}
