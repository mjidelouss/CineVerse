import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {StorageService} from "./storage.service";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";

export interface AuthResponseData {
  id : number,
  email : string,
  roles : string[],
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  AuthenticatedUser$  = new BehaviorSubject<User | null>(null);

  constructor(
    private http: HttpClient,
    private storageService: StorageService
  ) { }

  login(email : string, password: string) {
    return this.http.request<AuthResponseData>('post','http://localhost:8086/api/v1/auth/authenticate',
      {
        body : {email, password},
        withCredentials : true
      }).pipe(
      catchError(err => {
        let errorMessage = 'An unknown error occurred!';
        if(err.error.message === 'Bad credentials') {
          errorMessage = 'The email address or password you entered is invalid'
        }
        return throwError(() =>  new Error(errorMessage))
      }),
      tap(
        user => {
          const extractedUser : User = {
            email: user.email,
            id: user.id,
            role : {
              name : user.roles.find(role => role.includes('ROLE')) || '',
              permissions : user.roles.filter(permission => !permission.includes('ROLE'))
            }
          }
          this.storageService.saveUser(extractedUser);
          this.AuthenticatedUser$.next(extractedUser);
        }
      )
    );
  }
}
