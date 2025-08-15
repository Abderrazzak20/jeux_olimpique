import { routes } from './app.routes';
import { AutherService } from './services/auther-service';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private AutherS: AutherService, private routes: Router) { }



    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.AutherS.getToken();

        if (!token) {
            return next.handle(req);
        }

        if (this.AutherS.isTokenExpired(token)) {
            console.warn("Token expiré, déconnexion...");
            this.AutherS.logout();
            this.routes.navigate(["/login"]);
            return throwError(()=>new Error("Token expiré"));
        }

        const cloned = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });

        return next.handle(cloned);
    }


}

