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
import {ListService} from "../../service/list.service";

@Component({
  selector: 'app-admin-lists',
  standalone: true,
  templateUrl: './admin-lists.component.html',
  imports: [RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule, MatPaginatorModule, FormsModule],
  styleUrls: ['./admin-lists.component.scss']
})
export class AdminListsComponent {

  lists: any[] = [];
  selectedStatus: string = '';
  pageSizeOptions: number[] = [5, 10, 20];
  pageSize: number = 5;
  pageIndex: number = 0;
  totalLists: number = 0;
  AuthUserSub! : Subscription;

  constructor(private listService: ListService, private router: Router, private authService : AuthService,) {}
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
    if (this.router.url === "/dashboard-lists") {
      this.router.navigate(["/dashboard-lists"]);
    }
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    this.getLists();
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['dashboard-lists']);
          } else {
            this.router.navigate(['forbidden']);
          }
        } else {
          console.log("user is null")
        }
      }
    })
  }

  getLists() {
    this.listService
      .getLists(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.lists = response.data;
        },
        (error) => {
          console.error('Error fetching Lists:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getLists();
  }

  viewList(competition: any) {
    this.router.navigate(['/list-detail'], { state: { competition } });
  }

  editList(competition: any) {
    this.router.navigate(['/edit-list'], { state: { competition } });
  }

  deleteList(id: number) {

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
