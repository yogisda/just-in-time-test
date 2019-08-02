import { Injectable } from '@angular/core';
import {GenericService} from "../generic.service";
import {Project} from "../../models/Project";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {TimeBooking} from "../../models/TimeBooking";

@Injectable({
  providedIn: 'root'
})
export class TimeBookingService extends GenericService<TimeBooking> {

  constructor(http: HttpClient) {
      super(http);
      this.url =
          environment.backendApi.url +
          environment.backendApi.basePath +
          environment.backendApi.paths.timebooking;

  }

  parseDates(o: TimeBooking) {
      super.parseDates(o);
      o.startTime = new Date(o.startTime);
      o.endTime = new Date(o.endTime);
  }

}
