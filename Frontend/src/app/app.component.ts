import { Component } from '@angular/core';
import { NavigationService } from './service/navigation.service';
import {AuthService} from "./service/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  constructor(private navigationService: NavigationService, private authService: AuthService) {}

  ngOnInit() {
    //this.navigationService.navigateToHome();
    this.authService.autoLogin();
  }


}
