import { Injectable } from '@angular/core';
import {User} from "../models/user";

const USER_KEY = 'authenticated-user';
const ACCESS_TOKEN = "access-token"
const REFRESH_TOKEN = "refresh-token"

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  saveUser(user : User){
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  saveJwtToken(accessToken: string, refreshToken: string) {
    window.localStorage.removeItem(ACCESS_TOKEN);
    window.localStorage.removeItem(REFRESH_TOKEN);
    window.localStorage.setItem(ACCESS_TOKEN, JSON.stringify(accessToken));
    window.localStorage.setItem(REFRESH_TOKEN, JSON.stringify(refreshToken));
  }
  getSavedUser() : User | null {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  getAccessToken(): string | null {
    const accessToken = window.localStorage.getItem(ACCESS_TOKEN);
    if (accessToken) {
      return JSON.parse(accessToken);
    }
    return null;
  }

  getRefreshToken(): string | null {
    const refreshToken = window.localStorage.getItem(REFRESH_TOKEN);
    if (refreshToken) {
      return JSON.parse(refreshToken);
    }
    return null;
  }

  clean(): void {
    window.localStorage.clear();
  }
}
