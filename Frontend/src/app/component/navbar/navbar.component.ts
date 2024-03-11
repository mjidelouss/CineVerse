import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DomSanitizer} from "@angular/platform-browser";
import {MovieService} from "../../service/movie.service";
import {ReviewService} from "../../service/review.service";
import {AuthService} from "../../service/auth.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  fullname!: string
  profileImage!: string
  AuthUserSub! : Subscription;

  constructor(private router: Router, private authService: AuthService) {

  }
  handleLogout(event: Event) {
    event.preventDefault();
    this.authService.logout();
  }
  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          //this.fullname = user.id;
        }
      }
    });
  }



}
