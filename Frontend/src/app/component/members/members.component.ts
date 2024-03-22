import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {TrendingMovie} from "../../models/trendingMovie";
import {Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.scss']
})
export class MembersComponent {

  AuthUserSub! : Subscription;
  users: any[] = []
  pageIndex: number = 1
  pageSize: number = 4
  totalUsers: number = 100
  constructor(private router: Router, private userService: UserService, private authService: AuthService) {

  }

  ngOnInit() {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['forbidden']);
          }
        } else {
          this.router.navigate(['']);
        }
      }
    })
    this.getUsers()
  }

  onUserClick(userId: number): void {
    this.router.navigate(['/profile', userId]);
  }

  getUsers() {
    this.userService.getUsers(this.pageIndex, this.pageSize).subscribe(
      (response) => {
        console.log(response.data)
        this.users = response.data
      },
      (error) => {
        console.error('Error fetching Users:', error);
      }
    )
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getUsers();
  }


}
