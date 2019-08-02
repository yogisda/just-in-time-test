import { HttpRequest, HttpInterceptor, HttpEvent, HttpHandler, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, pipe } from "rxjs";
import { tap } from 'rxjs/operators';
import { Router } from "@angular/router";
import { AuthService } from "./services/auth/authenticate.service";

@Injectable()
export class RestInterceptor implements HttpInterceptor {

    constructor(private router: Router, private authService: AuthService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(this.authService.isAuthenticated()){
            request = request.clone({
                setHeaders:{
                    Authorization: 'Bearer ' +this.authService.getToken().token
                },
            });
        }

        return next.handle(request).pipe(tap(event => { }, err => {
            if (err instanceof HttpErrorResponse && err.status == 401) {
                console.error('Authorization Error');
                this.authService.setAuthenticated(false);
                this.router.navigate(['/login']);
            }
        }));
    }
}