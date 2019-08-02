import { Injectable } from '@angular/core';
import {GenericService} from "../generic.service";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import { Customer } from '../../models/Customer';
import {Role} from "../../models/Role";
import {Observable, Subject} from "rxjs";
import {of} from "rxjs/internal/observable/of";

@Injectable({
  providedIn: 'root'
})
export class RoleService extends GenericService<Role> {

  constructor(http: HttpClient) {
      super(http);
      this.url =
          environment.backendApi.url +
          environment.backendApi.basePath +
          environment.backendApi.paths.roles;

  }

  parseDates(o: Role) {
      super.parseDates(o);
  }

  getAll(query: string = ''): Observable<Role[]> {
      let roles:Role[] =  [
          {description: 'Manager', id:1, parseDates() {}},
          {description:'Mitarbeiter', id:2, parseDates() {}}
          ];
      return of(roles);
  }

}
