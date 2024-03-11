import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/user'
  constructor() { }

  getUser() {
  }

}
