import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  constructor(private router: Router) {}

  navigateToHome() {
    this.router.navigateByUrl('/home');
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }

  navigateToSignUp() {
    this.router.navigateByUrl('/sign-up')
  }

  navigateToUserHome() {
    this.router.navigateByUrl('/user-home')
  }

  navigateToProfile() {
    this.router.navigateByUrl('/profile')
  }

  navigateToWatchlist() {
    this.router.navigateByUrl('/watchlist')
  }

  navigateToMovie() {
    this.router.navigateByUrl('/movie')
  }
}
