import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/user'
  constructor(private http: HttpClient) {}

  getUser(id: number) {
    return this.http.get<any>(this.apiUrl+`/${id}`);
  }

}
