import { Component } from '@angular/core';
import { NavigationService } from './service/navigation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  constructor(private navigationService: NavigationService) {}

  ngOnInit() {
    this.navigationService.navigateToLogin();
  }


}
