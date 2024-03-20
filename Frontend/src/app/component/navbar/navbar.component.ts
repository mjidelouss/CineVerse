import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DomSanitizer} from "@angular/platform-browser";
import {MovieService} from "../../service/movie.service";
import {ReviewService} from "../../service/review.service";
import {AuthService} from "../../service/auth.service";
import {debounceTime, Subscription} from "rxjs";
import {UserService} from "../../service/user.service";
import {NavigationService} from "../../service/navigation.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  AuthUserSub! : Subscription;
  userId!: number
  searchResults: any[] = [];
  searchTermControl: FormControl = new FormControl();
  user!: any

  constructor(private router: Router, private movieService: MovieService, private authService: AuthService, private userService: UserService, private navigationService: NavigationService) {

  }
  handleLogout(event: Event) {
    event.preventDefault();
    this.authService.logout();
  }
  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
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

  onSettingsClick(): void {
    this.router.navigate(['/settings', this.userId]);
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.userId = user.id
        }
      }
    });
    this.searchTermControl.valueChanges.pipe(
      debounceTime(300) // Adjust the debounce time as needed (e.g., 300 milliseconds)
    ).subscribe(() => {
      this.onSearch();
    });
    this.getUser()
  }

  getUser() {
    this.userService.getUser(this.userId).subscribe(
      (response) => {
        this.user = response.data
      },
      (error) => {
        console.error('Error fetching User:', error);
      }
    )
  }

}
