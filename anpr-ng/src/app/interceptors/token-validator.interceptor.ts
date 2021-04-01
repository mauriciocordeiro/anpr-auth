import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { SnackBarService } from '../core/services/snackbar.service';

@Injectable()
export class TokenValidatorInterceptor implements HttpInterceptor {

  constructor(private snackBar: SnackBarService, private authService: AuthService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.includes('/auth')) {
      return next.handle(request);
    } else {
      let authReq = request;
      if (this.authService.isTokenValid()) {
        authReq = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + this.authService.getToken()) });
      } else {
        this.snackBar.alert('Autorização expirada. Por favor, autentique-se.');
        this.authService.logout();
      }
      return next.handle(authReq);
    }
  }
}
