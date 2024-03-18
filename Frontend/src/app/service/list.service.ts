import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ListService {

  private apiUrl = 'http://127.0.0.1:8080/api/v1/list';

  constructor(private http: HttpClient) {}

  getLists(pageIndex: number, pageSize: number): Observable<any> {
    const params = new HttpParams().set('page', pageIndex.toString()).set('size', pageSize.toString());
    return this.http.get<any>(this.apiUrl+'/all', { params });
  }

}
