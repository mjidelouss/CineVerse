import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { UserHomeComponent } from './component/user-home/user-home.component';
import { ProfileComponent } from './component/profile/profile.component';
import { WatchlistComponent } from './component/watchlist/watchlist.component';
import { MovieComponent } from './component/movie/movie.component';
import { LikesComponent } from './component/likes/likes.component';
import { DiaryComponent } from './component/diary/diary.component';
import { PofileListsComponent } from './component/pofile-lists/pofile-lists.component';
import { PofileMoviesComponent } from './component/pofile-movies/pofile-movies.component';
import { PofileReviewsComponent } from './component/pofile-reviews/pofile-reviews.component';
import {ForbiddenComponent} from "./component/forbidden/forbidden.component";
import {SettingsComponent} from "./component/settings/settings.component";
import {AddListComponent} from "./component/add-list/add-list.component";
import {EditListComponent} from "./component/edit-list/edit-list.component";
import {ListsComponent} from "./component/lists/lists.component";
import {MoviesComponent} from "./component/movies/movies.component";
import {MembersComponent} from "./component/members/members.component";
import {AdminMoviesComponent} from "./component/admin-movies/admin-movies.component";

export const Approutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [

      {
        path: 'component',
        loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule)
      },
    ]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'dashboard-movies',
    component: AdminMoviesComponent,
  },
  {
    path: 'forbidden',
    component: ForbiddenComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent
  },
  {
    path: 'user-home',
    component: UserHomeComponent
  },
  {
    path: 'add-list',
    component: AddListComponent
  },
  {
    path: 'members',
    component: MembersComponent
  },
  {
    path: 'edit-list',
    component: EditListComponent
  },
  {
    path: 'lists',
    component: ListsComponent
  },
  {
    path: 'movies',
    component: MoviesComponent
  },
  {
    path: 'profile/:id',
    component: ProfileComponent
  },
  {
    path: 'watchlist/:id',
    component: WatchlistComponent
  },
  {
    path: 'movie/:id',
    component: MovieComponent
  },
  {
    path: 'likes/:id',
    component: LikesComponent
  },
  {
    path: 'profile-reviews/:id',
    component: PofileReviewsComponent
  },
  {
    path: 'profile-movies/:id',
    component: PofileMoviesComponent
  },
  {
    path: 'profile-lists/:id',
    component: PofileListsComponent
  },
  {
    path: 'diary/:id',
    component: DiaryComponent
  },
  {
    path: 'settings/:id',
    component: SettingsComponent
  },
  {
    path: '**',
    redirectTo: '/starter'
  }
];
