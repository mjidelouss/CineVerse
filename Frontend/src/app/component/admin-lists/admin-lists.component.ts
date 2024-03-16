import {Component, HostListener} from '@angular/core';
import {Subscription} from "rxjs";
import {MovieService} from "../../service/movie.service";
import {Router, RouterModule} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {SidebarComponent} from "../../shared/sidebar/sidebar.component";
import {NavigationComponent} from "../../shared/header/navigation.component";
import {CommonModule} from "@angular/common";
import {NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {DataTablesModule} from "angular-datatables";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-admin-lists',
  standalone: true,
  templateUrl: './admin-lists.component.html',
  imports: [RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule, MatPaginatorModule, FormsModule],
  styleUrls: ['./admin-lists.component.scss']
})
export class AdminListsComponent {

  competitions: any[] = [];
  selectedStatus: string = '';
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize: number = 5;
  pageIndex: number = 0;
  totalCompetitions: number = 0;
  AuthUserSub! : Subscription;

  constructor(private movieService: MovieService, private router: Router, private authService : AuthService,) {}
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
    if (this.router.url === "/dashboard-movies") {
      this.router.navigate(["/dashboard-movies"]);
    }
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    this.getCompetitions();
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_MANAGER") {
            this.router.navigate(['competition']);
          } else if (user.role.name == "ROLE_JURY") {
            this.router.navigate(['jury-dashboard']);
          } else {
            this.router.navigate(['member-dashboard']);
          }
        } else {
          console.log("user is null")
        }
      }
    })
  }

  getCompetitions() {
    this.movieService
      .getMovies(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.competitions = response.data;
          this.totalCompetitions = response.data.length;
        },
        (error) => {
          console.error('Error fetching Competitions:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getCompetitions();
  }

  getCompetitionsByStatus() {
    this.movieService.getMoviesByStatus(this.selectedStatus).subscribe(
      (response) => {
        this.competitions = response.data;
      },
      (error) => {
        console.error('Error fetching competitions:', error);
      }
    );
  }

  viewCompetition(competition: any) {
    this.router.navigate(['/competition-detail'], { state: { competition } });
  }

  editCompetition(competition: any) {
    this.router.navigate(['/edit-competition'], { state: { competition } });
  }

  deleteCompetition(id: number) {

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
