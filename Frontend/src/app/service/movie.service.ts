import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/movie';

  constructor(private http: HttpClient) {}

  getTrendingMovies() {
    return this.http.get<any>(this.apiUrl + "/trending");
  }

  searchMovies(searchTerm: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search?query=${searchTerm}`);
  }

  filterMoviesByGenre(userId: number, genre: string): Observable<any> {
    return this.http.get(this.apiUrl+ `/filterByGenre/${userId}?genre=${genre}`)
  }

  filterMoviesByDecade(userId: number, decade: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/filterByDecade/${userId}?decade=${decade}`);
  }

  getMovieById(id: number) {
    return this.http.get<any>(`http://127.0.0.1:8080/api/v1/movie/${id}`);
  }

  getSimilarMovies(id: number) {
    return this.http.get<any>(`http://127.0.0.1:8080/api/v1/movie/similar/${id}`);
  }

  getLastMovies() {
    return this.http.get<any>(`http://127.0.0.1:8080/api/v1/movie/last`);
  }
  getMovies(pageIndex: number, pageSize: number): Observable<any> {
    const params = new HttpParams().set('page', pageIndex.toString()).set('size', pageSize.toString());
    return this.http.get<any>(this.apiUrl+'/all', { params });
  }

  getTotalMovies() {
    return this.http.get<any>(this.apiUrl+`/count`);
  }
  deleteMovie(id: number): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url);
  }
}
