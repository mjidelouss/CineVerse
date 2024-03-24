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
  fileInputs: { [key: string]: File } = {};
  user!: any;
  loader = true;
  showAlert: boolean = false;

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
    setTimeout(() => {
      this.loader = false;
    }, 2000);
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
    const formData = new FormData();
    formData.append("firstname", this.user.firstname)
    formData.append("lastname", this.user.lastname)
    formData.append("email", this.user.email)
    formData.append("bio", this.user.bio)
    formData.append("location", this.user.location)
    if (this.fileInputs["image"]) {
      formData.append("image", this.fileInputs["image"]);
    }
    this.userService.updateUserProfile(this.userId, formData).subscribe(
      (response: any) => {
        this.showAlert = true;
        setTimeout(() => this.showAlert = false, 5000);
        this.user = response.data
      },
      (error) => {
        console.error('Error updating User profile:', error);
      }
    );
  }

  onFileChange(event: any, field: string) {
    if (event.target.files.length > 0) {
      this.fileInputs[field] = event.target.files[0];
    }
  }

}
