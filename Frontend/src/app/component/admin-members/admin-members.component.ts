import {Component, HostListener} from '@angular/core';
import {Subscription} from "rxjs";
import {MovieService} from "../../service/movie.service";
import {Router, RouterModule} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {SidebarComponent} from "../../shared/sidebar/sidebar.component";
import {NavigationComponent} from "../../shared/header/navigation.component";
import {CommonModule} from "@angular/common";
import {NgbAlert, NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {DataTablesModule} from "angular-datatables";
import {FormsModule} from "@angular/forms";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-admin-members',
  standalone: true,
  templateUrl: './admin-members.component.html',
  imports: [RouterModule, SidebarComponent, NavigationComponent, CommonModule, NgbCollapseModule, DataTablesModule, MatPaginatorModule, FormsModule, NgbAlert],
  styleUrls: ['./admin-members.component.scss']
})
export class AdminMembersComponent {
  showAlert: boolean = false;
  members: any[] = [];
  pageSizeOptions: number[] = [4];
  pageSize: number = 4;
  pageIndex: number = 0;
  totalMembers: number = 50;
  AuthUserSub! : Subscription;

  constructor(private userService: UserService, private router: Router, private authService : AuthService,) {}
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
    if (this.router.url === "/dashboard-members") {
      this.router.navigate(["/dashboard-members"]);
    }
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    this.getMembers();
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next : user => {
        if(user) {
          if (user.role.name == "ROLE_ADMIN") {
            this.router.navigate(['dashboard-members']);
          } else {
            this.router.navigate(['forbidden']);
          }
        } else {
          console.log("user is null")
        }
      }
    })
  }

  getMembers() {
    this.userService
      .getUsers(this.pageIndex, this.pageSize)
      .subscribe(
        (response) => {
          this.members = response.data.map((member: any) => ({
            id: member.userId,
            firstname: member.firstname || 'N/A',
            lastname: member.lastname || 'N/A',
            bio: member.bio || 'N/A',
            email: member.email,
            location: member.location || 'N/A',
            image: member.image
          }));
        },
        (error) => {
          console.error('Error fetching Members:', error);
        }
      );
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getMembers();
  }
  deleteMember(id: number) {
    this.userService.deleteUser(id).subscribe(
      (response) => {
        this.showAlert = true;
        setTimeout(() => this.showAlert = false, 5000);
      },
      (error) => {
        console.error('Error Deleting User:', error);
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
