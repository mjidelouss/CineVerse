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

export const Approutes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: FullComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path: 'component',
        loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule)
      },
    ]
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
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'watchlist',
    component: WatchlistComponent
  },
  {
    path: 'movie/:id',
    component: MovieComponent
  },
  {
    path: 'likes',
    component: LikesComponent
  },
  {
    path: 'profile-reviews',
    component: PofileReviewsComponent
  },
  {
    path: 'profile-movies',
    component: PofileMoviesComponent
  },
  {
    path: 'profile-lists',
    component: PofileListsComponent
  },
  {
    path: 'diary',
    component: DiaryComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: '**',
    redirectTo: '/starter'
  }
];
