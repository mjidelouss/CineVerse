import {Component, AfterViewInit, EventEmitter, Output, OnInit, OnDestroy} from '@angular/core';
import { NgbDropdownModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {debounceTime, Subscription} from "rxjs";
import {AuthService} from "../../service/auth.service";

declare var $: any;

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports:[NgbDropdownModule],
  templateUrl: './navigation.component.html'
})
export class NavigationComponent implements AfterViewInit, OnInit, OnDestroy {
  @Output() toggleSidebar = new EventEmitter<void>();

  AuthUserSub! : Subscription;
  userId!: number

  public showSearch = false;

  constructor(private modalService: NgbModal, private authService: AuthService) {
  }

  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }


  handleLogout(event: Event) {
    event.preventDefault();
    this.authService.logout();
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.userId = user.id
        }
      }
    });
  }

  ngAfterViewInit() { }
}
