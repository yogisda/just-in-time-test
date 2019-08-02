import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { map, retry, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { ApiObject } from '../Models/ApiObject';

@Injectable()
export class GenericService<T extends ApiObject> {
  constructor(protected http: HttpClient) {}

  url = environment.backendApi.url + environment.backendApi.basePath;

  getAll(query: string = ''): Observable<T[]> {
    return this.http.get<T[]>(this.url + query).pipe(
      map((data: T[]) => {
        data.forEach(o => {
          this.parseDates(o);
        });
        return data;
      }),
      retry(3), // retry a failed request up to 3 times
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }


  get(id: number): Observable<T> {
    return this.http.get<T>(this.url + '/' + id).pipe(
      map((data: T) => {
        this.parseDates(data);
        return data;
      }),
      retry(3), // retry a failed request up to 3 times
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  update(object: T): Observable<T> {
    let o = this.stripCopyObject(object);
    let id = object.id;
    return this.http.put<T>(this.url + '/' + id, o).pipe(
      map((data: T) => {
        // console.log('Done');
        this.parseDates(data);
        return data;
      }),
      retry(3), // retry a failed request up to 3 times
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  create(object: T): Observable<T> {
    let o = this.stripCopyObject(object);
    delete object.id;
    return this.http.post<T>(this.url, o).pipe(
      map((data: T) => {
        this.parseDates(data);
        return data;
      }),
      retry(3), // retry a failed request up to 3 times
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  delete(object: T): Observable<T> {
    return this.http.delete<T>(this.url + '/' + object.id).pipe(
      map((data: T) => {
        this.parseDates(data);
        return data;
      }),
      retry(3), // retry a failed request up to 3 times
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  parseDates(o: T) {
    // o.createdAt = new Date(o.createdAt);
    // o.updatedAt = new Date(o.updatedAt);
  }

  private stripCopyObject(o: T): T {
    let object: any = {};
    for (const key in o) {
      if (o.hasOwnProperty(key) && o[key] !== null) {
        if (typeof o[key] === 'object' && o[key].hasOwnProperty('length') && o[key][0].hasOwnProperty('id')) {
          // console.debug('Array', key);
          object[key] = [];
          for (let i = 0; i < (o[key] as any).length; i++) {
            (object[key] as any)[i] = (o[key][i] as any).id;
          }
        } else if (typeof o[key] === 'object' && o[key].hasOwnProperty('id')) {
          // console.debug('ApiObject: ', key, '\n \t', o[key]);
          // Must be ApiObject
          object[key] = (o[key] as any).id;
        } else {
          // console.debug('Other: ', key, '\n \t', o[key]);
          // Don't Change it
          object[key] = o[key];
        }
      }
    }
    return object;
  }
}
