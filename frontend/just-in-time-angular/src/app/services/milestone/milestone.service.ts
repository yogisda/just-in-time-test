import { Injectable } from '@angular/core';
import {GenericService} from "../generic.service";
import {Milestone} from "../../models/Milestone";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MilestoneService extends GenericService<Milestone> {

  constructor(http: HttpClient) {
      super(http);
      this.url =
          environment.backendApi.url +
          environment.backendApi.basePath +
          environment.backendApi.paths.milestone;
  }

  parseDates(o: Milestone) {
      super.parseDates(o);
      o.endDate = new Date(o.endDate);
  }
}