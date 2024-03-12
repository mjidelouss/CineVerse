import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LikeService {
  private apiUrl = 'http://127.0.0.1:8080/api/v1/like'
  constructor(private http: HttpClient) {}

  saveReviewLike(userId: number, reviewId: number) {
    const url = `${this.apiUrl}/${userId}/${reviewId}`;
    return this.http.post<any>(url, {});
  }

}
