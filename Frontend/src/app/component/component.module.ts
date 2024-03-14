import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsRoutes } from './component.routing';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { MatIconModule } from '@angular/material/icon';
import { UserHomeComponent } from './user-home/user-home.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoginComponent } from './login/login.component';
import { MovieComponent } from './movie/movie.component';
import { ProfileComponent } from './profile/profile.component';
import { WatchlistComponent } from './watchlist/watchlist.component';
import { PofileMoviesComponent } from './pofile-movies/pofile-movies.component';
import { PofileListsComponent } from './pofile-lists/pofile-lists.component';
import { PofileReviewsComponent } from './pofile-reviews/pofile-reviews.component';
import { LikesComponent } from './likes/likes.component';
import { DiaryComponent } from './diary/diary.component';
import { SettingsComponent } from './settings/settings.component';
import { MembersComponent } from './members/members.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import { AddListComponent } from './add-list/add-list.component';
import { EditListComponent } from './edit-list/edit-list.component';
import { MoviesComponent } from './movies/movies.component';
import { ListsComponent } from './lists/lists.component';



@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(ComponentsRoutes),
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        MatIconModule,
        MatButtonModule,
        MatDialogModule
    ],
  declarations: [


    DashboardComponent,
    HomeComponent,
    NavbarComponent,
    FooterComponent,
    UserHomeComponent,
    SignUpComponent,
    LoginComponent,
    MovieComponent,
    ProfileComponent,
    WatchlistComponent,
    PofileMoviesComponent,
    PofileListsComponent,
    PofileReviewsComponent,
    LikesComponent,
    DiaryComponent,
    SettingsComponent,
    MembersComponent,
    ForbiddenComponent,
    AddListComponent,
    EditListComponent,
    MoviesComponent,
    ListsComponent,
  ],
})
export class ComponentsModule { }
