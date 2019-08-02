import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable, throwError, of } from 'rxjs';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { map, retry, catchError } from 'rxjs/operators';
import { Token } from '../../Models/token';

@Injectable()
export class AuthService {
  private token: Token = new Token();

  private authenticatedSource = new BehaviorSubject<boolean>(false);
  authenticated = this.authenticatedSource.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    // super(storage);
    this.loadFromSession();
  }

  isAuthenticated(): boolean {
    if (this.token && this.token.isValid()) {
      return true;
    } else {
      return false;
    }
  }

  getToken() {
    if (this.token.isValid()) {
      // console.log(this.token);
      return this.token;
    } else {
      false;
    }
  }

  loginUrl =
    environment.backendApi.url +
    environment.backendApi.authPath +
    environment.backendApi.authPaths.login;
  logoutUrl =
    environment.backendApi.url +
    environment.backendApi.authPath +
    environment.backendApi.authPaths.logout;

  public loggedin: boolean;

  clear(): void {
    this.clearFromSession();
  }

  saveToSession(): void {
    document.cookie = 'token=' + this.token.token + '; path=/';
  }

  loadFromSession(): void {
    if (document.cookie.length > 0) {
      let c_start = document.cookie.indexOf('token' + '=');
      if (c_start != -1) {
        c_start = c_start + 'token'.length + 1;
        let c_end = document.cookie.indexOf(';', c_start);
        if (c_end == -1) {
          c_end = document.cookie.length;
        }
        this.token.token = unescape(document.cookie.substring(c_start, c_end));
        this.token.fill();
        this.setAuthenticated(true);
      }
    }
  }

  clearFromSession(): void {
    document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    this.token = new Token();
  }

  setAuthenticated(auth) {
    if (auth && this.token.isValid()) {
      this.authenticatedSource.next(true);

    } else {
      this.authenticatedSource.next(false);
    }
  }

  login(email, password): Observable<any> {
    return this.http
      .put<any>(this.loginUrl, { email: email, password: password })
      .pipe(
        map((data: Token) => {
          this.token.token = data.token;
          if (this.token.isValid()) {
            this.token.fill();
            this.saveToSession();
          }
          this.setAuthenticated(true);
          return true;
        }),
        catchError(this.handleError) // then handle the error
      );
  }

  logout(): void {
    this.clear();
    this.setAuthenticated(false);
    this.router.navigate(['/']);
    // location.reload();

  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
