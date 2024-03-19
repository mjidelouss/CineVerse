import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/watchlist'
  constructor(private http: HttpClient) {}

  getUserWatchlist(userId: number):Observable<any> {
    const url = `${this.apiUrl}/user/${userId}`;
    return this.http.get(url);
  }
  addMovieToWatchList(watchList: any) {
    return this.http.post<any>(this.apiUrl, watchList);
  }

  removeMovieFromWatchList(watchList: any) {
    return this.http.post<any>(this.apiUrl+`/remove`, watchList);
  }

  filterWatchedMoviesByGenre(userId: number, genre: string) : Observable<any> {
    const url = `${this.apiUrl}/filterWatchedMoviesByGenre/${userId}?genre=${genre}`;
    return this.http.get<any>(url);
  }

  filterWatchedMoviesByDecade(userId: number, decade: string) : Observable<any> {
    const url = `${this.apiUrl}/filterWatchedMoviesByDecade/${userId}?decade=${decade}`;
    return this.http.get<any>(url);
  }
}
