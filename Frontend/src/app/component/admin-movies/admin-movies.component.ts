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
import {Movie} from "../../models/movie";

@Component({
  selector: 'app-admin-movies',
  standalone: true,
  templateUrl: './admin-movies.component.html',
  imports: [RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule, MatPaginatorModule, FormsModule],
  styleUrls: ['./admin-movies.component.scss']
})
export class AdminMoviesComponent {

  movies: Movie[] = [];
  selectedStatus: string = '';
  pageSizeOptions: number[] = [4];
  pageSize: number = 4;
  pageIndex: number = 0;
  totalMovies: number = 100;
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
    this.getMovies();
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['admin-movies']);
          } else {
            this.router.navigate(['forbidden']);
          }
        } else {
          console.log("user is null")
        }
      }
    })
  }

  getMovies() {
    this.movieService
      .getMovies(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.movies = response.data;
        },
        (error) => {
          console.error('Error fetching Movies:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getMovies();
  }

  getMoviesByGenre() {
    this.movieService.getMoviesByStatus(this.selectedStatus).subscribe(
      (response) => {
        this.movies = response.data;
      },
      (error) => {
        console.error('Error fetching movies:', error);
      }
    );
  }

  viewMovie(competition: any) {
    this.router.navigate(['/movie-detail'], { state: { competition } });
  }

  editMovie(competition: any) {
    this.router.navigate(['/edit-movie'], { state: { competition } });
  }

  deleteMovie(id: number) {

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
