import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {Genre} from "../../models/genre";
import {ReviewService} from "../../service/review.service";
import {GenreService} from "../../service/genre.service";

@Component({
  selector: 'app-pofile-reviews',
  templateUrl: './pofile-reviews.component.html',
  styleUrls: ['./pofile-reviews.component.scss']
})
export class PofileReviewsComponent implements OnInit, OnDestroy{

  userId!: number;
  reviews: any[] = [];
  selectedDecade!: string;
  genres: Genre[] = [];
  selectedGenre: string = '';
  loader = true

  constructor(private route: ActivatedRoute,private router: Router, private genreService: GenreService,
              public dialog: MatDialog, private reviewService: ReviewService) {

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
    setTimeout(() => {
      this.loader = false;
    }, 2000);
  }

  getMyReviews() {
    this.reviewService.getUserReviews(this.userId).subscribe(
      (response) => {
        this.reviews = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: any = {
            id: dbMovie.movieId,
            reviewId: dbMovie.reviewId,
            language: dbMovie.movieLanguage,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.year || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            like: dbMovie.liked,
            rate: dbMovie.rating,
            content: dbMovie.content,
            date: new Date(dbMovie.timestamp).getFullYear(),
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.reviews.push(movie);
        }
      },
      (error) => {
        console.error('Error fetching Reviews:', error);
      }
    )
  }

  filterReviewdMoviesByGenre() {
    this.reviewService.filterDiaryMoviesByGenre(this.userId, this.selectedGenre).subscribe(
      (response) => {
        this.reviews = [];
        for (const element of response.data) {
          const dbMovie = element;
          let movie: any = {
            id: dbMovie.movieId,
            reviewId: dbMovie.reviewId,
            language: dbMovie.movieLanguage,
            title: dbMovie.movieTitle || 'N/A',
            year: dbMovie.year || 'N/A',
            image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
            like: dbMovie.liked,
            rate: dbMovie.rating,
            content: dbMovie.content,
            date: new Date(dbMovie.timestamp).getFullYear(),
            timestamp: this.parseTimeStamp(dbMovie.timestamp)
          };
          this.reviews.push(movie);
        }
      },
      (error) => {
        console.error('Error filtering Reviews By Genre:', error);
      }
    )
  }

  filterReviewdMoviesByDecade() {
    if (this.selectedDecade) {
      this.reviewService.filterDiaryMoviesByDecade(this.userId, this.selectedDecade).subscribe(
        (response) => {
          this.reviews = [];
          for (const element of response.data) {
            const dbMovie = element;
            let movie: any = {
              id: dbMovie.movieId,
              reviewId: dbMovie.reviewId,
              language: dbMovie.movieLanguage,
              title: dbMovie.movieTitle || 'N/A',
              year: dbMovie.year || 'N/A',
              image: "https://image.tmdb.org/t/p/w500/" + dbMovie.movieImage || 'N/A',
              like: dbMovie.liked,
              rate: dbMovie.rating,
              content: dbMovie.content,
              date: new Date(dbMovie.timestamp).getFullYear(),
              timestamp: this.parseTimeStamp(dbMovie.timestamp)
            };
            this.reviews.push(movie);
          }
        },
        (error) => {
          console.error('Error filtering Reviews By Decade:', error);
        }
      )
    } else {
      this.getMyReviews()
    }

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

  onReviewsClick(): void {
    this.router.navigate(['/profile-reviews', this.userId]);
  }


}
