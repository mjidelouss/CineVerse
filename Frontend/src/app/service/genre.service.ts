import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/genre';

  constructor(private http: HttpClient) {}
  getGenres(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

}
