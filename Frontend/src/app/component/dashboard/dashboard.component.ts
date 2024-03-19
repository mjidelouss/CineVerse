import {Component, HostListener} from '@angular/core';
import {Router, RouterModule} from "@angular/router";
import {SidebarComponent} from "../../shared/sidebar/sidebar.component";
import {NavigationComponent} from "../../shared/header/navigation.component";
import {CommonModule} from "@angular/common";
import {NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {DataTablesModule} from "angular-datatables";
import {UserService} from "../../service/user.service";
import {MovieService} from "../../service/movie.service";
import {ReviewService} from "../../service/review.service";
import {Diary} from "../../models/diary";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  imports:[RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule],
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  totalUsers!: number
  totalMovies!: number
  totalReviews!: number

  constructor(public router: Router, private userService: UserService, private movieService: MovieService,
              private reviewService: ReviewService) {}
  public isCollapsed = false;
  public innerWidth: number = 0;
  public defaultSidebar: string = "";
  public showMobileMenu = false;
  public expandLogo = false;
  public sidebartype = "full";

  Logo() {
    this.expandLogo = !this.expandLogo;
  }

  ngOnInit() {
    if (this.router.url === "/") {
      this.router.navigate(["/dashboard"]);
    }
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    this.getTotalUsers()
    this.getTotalReviews()
    this.getTotalMovies()
  }

  getTotalUsers() {
    this.userService.getTotalUsers().subscribe(
      (response) => {
        this.totalUsers = response.data
      },
      (error) => {
        console.error('Error fetching Total Users:', error);
      }
    )
  }

  getTotalReviews() {
    this.reviewService.getTotalReviews().subscribe(
      (response) => {
        this.totalReviews = response.data
      },
      (error) => {
        console.error('Error fetching Total Reviews:', error);
      }
    )
  }

  getTotalMovies() {
    this.movieService.getTotalMovies().subscribe(
      (response) => {
        this.totalMovies = response.data
      },
      (error) => {
        console.error('Error fetching Total Movies:', error);
      }
    )
  }

  @HostListener("window:resize", ["$event"])
  onResize() {
    this.handleSidebar();
  }

  handleSidebar() {
    this.innerWidth = window.innerWidth;
    if (this.innerWidth < 1170) {
      this.sidebartype = "full";
    } else {
      this.sidebartype = this.defaultSidebar;
    }
  }

  toggleSidebarType() {
    switch (this.sidebartype) {
      case "full":
        this.sidebartype = "mini-sidebar";
        break;

      case "mini-sidebar":
        this.sidebartype = "full";
        break;

      default:
    }
  }

}
