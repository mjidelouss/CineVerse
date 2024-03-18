import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MovieService} from "../../service/movie.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{

  trendingMovies: TrendingMovie[] = [];
  AuthUserSub! : Subscription;
  searchForm!: FormGroup;
  searchResults: any[] = [];
  searchTermControl: FormControl = new FormControl();

  constructor(private movieService: MovieService, private router: Router, private authService : AuthService, private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_MANAGER") {
            this.router.navigate(['dashboard']);
          } else if (user.role.name == "ROLE_USER") {
            this.router.navigate(['user-home']);
          }
        }
      }
    })
    this.searchTermControl.valueChanges.pipe(
      debounceTime(300) // Adjust the debounce time as needed (e.g., 300 milliseconds)
    ).subscribe(() => {
      this.onSearch();
    });
    this.getTrendingMovies();
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  onSearch(): void {
    const searchTerm = this.searchTermControl.value.trim();
    if (searchTerm == "") {
      this.searchResults = [];
    } else{
      this.movieService.searchMovies(searchTerm).subscribe(
        (response) => {
          this.searchResults = response.data;
        },
        (error) => {
          console.error('Error fetching movie search results:', error);
        }
      );
    }
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
