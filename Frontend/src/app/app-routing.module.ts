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
import { PofileMoviesComponent } from './component/pofile-movies/pofile-movies.component';
import { PofileReviewsComponent } from './component/pofile-reviews/pofile-reviews.component';
import {ForbiddenComponent} from "./component/forbidden/forbidden.component";
import {SettingsComponent} from "./component/settings/settings.component";
import {MoviesComponent} from "./component/movies/movies.component";
import {MembersComponent} from "./component/members/members.component";
import {AdminMoviesComponent} from "./component/admin-movies/admin-movies.component";
import {AdminMembersComponent} from "./component/admin-members/admin-members.component";
import {AdminReviewsComponent} from "./component/admin-reviews/admin-reviews.component";
import {UserProfileComponent} from "./component/user-profile/user-profile.component";
import {authGuard} from "./helpers/auth.guard";

export const Approutes: Routes = [
  {
    path: 'full',
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
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'dashboard-movies',
    component: AdminMoviesComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'dashboard-members',
    component: AdminMembersComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'dashboard-reviews',
    component: AdminReviewsComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN']},
  },
  {
    path: 'forbidden',
    component: ForbiddenComponent
  },
  {
    path: '',
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
    component: UserHomeComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'members',
    component: MembersComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'movies',
    component: MoviesComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'profile/:id',
    component: ProfileComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'watchlist/:id',
    component: WatchlistComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'movie/:id',
    component: MovieComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'likes/:id',
    component: LikesComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'user-profile/:id',
    component: UserProfileComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'profile-reviews/:id',
    component: PofileReviewsComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'profile-movies/:id',
    component: PofileMoviesComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'diary/:id',
    component: DiaryComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: 'settings/:id',
    component: SettingsComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_USER']},
  },
  {
    path: '**',
    redirectTo: '/starter'
  }
];
