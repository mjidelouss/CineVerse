import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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

  getReviews(pageIndex: number, pageSize: number): Observable<any> {
    const params = new HttpParams().set('page', pageIndex.toString()).set('size', pageSize.toString());
    return this.http.get<any>(this.apiUrl+'/all', { params });
  }
  addRating(rate: any, num: number): Observable<any> {
    const url = `${this.apiUrl}/rate/${num}`;
    return this.http.post(url, rate);
  }

  saveLike(rate: any, like: boolean): Observable<any> {
    const url = `${this.apiUrl}/like/${like}`;
    return this.http.post(url, rate);
  }

  saveWatchList(rate: any, watch: boolean): Observable<any> {
    const url = `${this.apiUrl}/watchlist/${watch}`;
    return this.http.post(url, rate);
  }

  addReview(review:any):Observable<any> {
    return this.http.post(this.apiUrl, review);
  }

  getReviewedMoviesWithRating(userId: number) {
    const url = `${this.apiUrl}/reviewed-movies/${userId}`;
    return this.http.get<any>(url);
  }

  getRecentReviews(movieId: number) {
    const url = `${this.apiUrl}/recent/${movieId}`;
    return this.http.get<any>(url);
  }

  getUserReviews(userId: number) {
    const url = `${this.apiUrl}/diary/${userId}`;
    return this.http.get<any>(url);
  }

  getUserLikedMovies(userId: number) {
    const url = `${this.apiUrl}/liked/${userId}`;
    return this.http.get<any>(url);
  }

  getRecentUserReviews(userId: number) {
    const url = `${this.apiUrl}/user-review/${userId}`;
    return this.http.get<any>(url);
  }

  filterLikedMoviesByGenre(userId: number, genre: string) : Observable<any> {
    const url = `${this.apiUrl}/filterLikedMoviesByGenre/${userId}?genre=${genre}`;
    return this.http.get<any>(url);
  }

  filterLikedMoviesByDecade(userId: number, decade: string) : Observable<any> {
    const url = `${this.apiUrl}/filterLikedMoviesByDecade/${userId}?decade=${decade}`;
    return this.http.get<any>(url);
  }

  filterDiaryMoviesByGenre(userId: number, genre: string) : Observable<any> {
    const url = `${this.apiUrl}/filterDiaryMoviesByGenre/${userId}?genre=${genre}`;
    return this.http.get<any>(url);
  }

  filterDiaryMoviesByDecade(userId: number, decade: string) : Observable<any> {
    const url = `${this.apiUrl}/filterDiaryMoviesByDecade/${userId}?decade=${decade}`;
    return this.http.get<any>(url);
  }

  getTotalReviews() {
    return this.http.get<any>(this.apiUrl+`/count`);
  }

}
