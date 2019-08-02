import { Injectable } from '@angular/core';
import { Permission } from "../../models/Permission";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { throwError, Observable } from 'rxjs';
import { ApiObject } from '../../models/ApiObject';
import { map, retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class PermissionService {

  constructor(protected http: HttpClient) {}

  url = environment.backendApi.url +
        environment.backendApi.basePath +
        environment.backendApi.paths.permission;

  hasWritePermission(type: string, object: ApiObject): Observable<boolean> {
    return this.http.get<Permission>(this.url + '/write?type=' + type + '&id=' + object.id).pipe(
      map((data: Permission) => {
        return data.hasPermission;
      }),
      retry(3),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }
}