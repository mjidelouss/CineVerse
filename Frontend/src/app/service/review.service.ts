import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/review';

  constructor(private http: HttpClient) {}

  getReview(movieId: number, userId: number):Observable<any> {
    const url = `${this.apiUrl}/${movieId}/${userId}`;
    return this.http.get(url);
  }
  addRating(rate: any, num: number): Observable<any> {
    const url = `${this.apiUrl}/rate/${num}`;
    return this.http.post(url, rate);
  }

  addReview(review:any):Observable<any> {
    console.log(review)
    return this.http.post(this.apiUrl, review);
  }

}
