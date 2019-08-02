import { Injectable } from '@angular/core';
import {GenericService} from "../generic.service";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import { Customer } from '../../models/Customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService extends GenericService<Customer> {

  constructor(http: HttpClient) {
      super(http);
      this.url =
          environment.backendApi.url +
          environment.backendApi.basePath +
          environment.backendApi.paths.customers;

  }

  parseDates(o: Customer) {
      super.parseDates(o);
  }

}
