import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get the Access Token and Refresh Token from the Local Storage
    const accessToken = localStorage.getItem("access-token");
    const refreshToken = localStorage.getItem("refresh-token");

    // Clone the request and add the Authorization header if JWT is present
    if (accessToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${accessToken.replace(/"/g, '')}`
        }
      });
    }

    if (refreshToken) {
      request = request.clone({
        setHeaders: {
          'Refresh-Token': refreshToken.replace(/"/g, '')
        }
      });
    }

    return next.handle(request);
  }
}
