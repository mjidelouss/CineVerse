import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/review';

  constructor(private http: HttpClient) {}

  addRating(review: any): Observable<any> {
    const url = `${this.apiUrl}/rate`;
    return this.http.post(url, review);
  }

}
