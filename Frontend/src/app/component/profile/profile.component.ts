import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  userId!: number
  user!: any

  constructor(private authService: AuthService, private userService: UserService, private route: ActivatedRoute) {
  }
  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
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
