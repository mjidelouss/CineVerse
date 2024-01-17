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
}