import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit, OnDestroy{
  userId!: number;
  user!: any;

  constructor(private route: ActivatedRoute,private router: Router, public dialog: MatDialog, private userService: UserService) {

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
    this.getUserProfile();
  }

  getUserProfile() {
    this.userService.getUser(this.userId).subscribe(
      (response) => {
        this.user = response.data
      },
      (error) => {
        console.error('Error fetching User:', error);
      }
    )
  }

  saveChanges() {
    // Call the user service to update the user's profile
    this.userService.updateUserProfile(this.userId, this.user).subscribe(
      (response: any) => {
        this.user = response.data
      },
      (error) => {
        console.error('Error updating User profile:', error);
      }
    );
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      // Set the profile image data to the base64 string
      this.user.image = reader.result as string;
    };
    // Read the selected file as a data URL
    reader.readAsDataURL(file);
  }

}
