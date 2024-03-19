import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/user'
  constructor(private http: HttpClient) {}

  getUser(id: number) {
    return this.http.get<any>(this.apiUrl+`/${id}`);
  }

  getUsers(pageIndex: number, pageSize: number): Observable<any> {
    const params = new HttpParams().set('page', pageIndex.toString()).set('size', pageSize.toString());
    return this.http.get<any>(this.apiUrl+'/all', { params });
  }

  updateUserProfile(userId: number, updatedUser: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/profile/${userId}`, updatedUser);
  }

}
