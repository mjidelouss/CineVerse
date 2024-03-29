import {Component, HostListener} from '@angular/core';
import {Subscription} from "rxjs";
import {Router, RouterModule} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {SidebarComponent} from "../../shared/sidebar/sidebar.component";
import {NavigationComponent} from "../../shared/header/navigation.component";
import {CommonModule} from "@angular/common";
import {NgbAlert, NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {DataTablesModule} from "angular-datatables";
import {FormsModule} from "@angular/forms";
import {ReviewService} from "../../service/review.service";

@Component({
  selector: 'app-admin-reviews',
  standalone: true,
  templateUrl: './admin-reviews.component.html',
    imports: [RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule, MatPaginatorModule, FormsModule, NgbAlert],
  styleUrls: ['./admin-reviews.component.scss']
})
export class AdminReviewsComponent {

  reviews: any[] = [];
  showAlert: boolean = false;
  pageSizeOptions: number[] = [4];
  pageSize: number = 4;
  pageIndex: number = 0;
  totalReviews: number = 100;
  AuthUserSub! : Subscription;

  constructor(private reviewService: ReviewService, private router: Router, private authService : AuthService,) {}
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
    if (this.router.url === "/dashboard-reviews") {
      this.router.navigate(["/dashboard-reviews"]);
    }
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    this.getReviews();
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['dashboard-review']);
          } else {
            this.router.navigate(['forbidden']);
          }
        } else {
          console.log("user is null")
        }
      }
    })
  }

  getReviews() {
    this.reviewService
      .getReviews(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.reviews = response.data.map((review: any) => ({
            id: review.reviewId,
            title: review.movieTitle || 'N/A',
            rating: review.rating || 'N/A',
            timestamp: review.timestamp || 'N/A',
            firstname: review.userFirstname,
          }));
        },
        (error) => {
          console.error('Error fetching Reviews:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getReviews();
  }
  deleteReview(id: number) {
    this.reviewService.deleteReview(id).subscribe(
      (response) => {
        this.showAlert = true;
        setTimeout(() => this.showAlert = false, 5000);
      },
      (error) => {
        console.error('Error Deleting Review:', error);
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
