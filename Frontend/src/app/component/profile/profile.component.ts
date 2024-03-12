import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  AuthUserSub! : Subscription;
  userId!: number
  user!: any

  constructor(private authService: AuthService, private userService: UserService) {
  }
  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.userId = user.id
        }
      }
    });
    this.getUser()
    console.log(this.user)
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
