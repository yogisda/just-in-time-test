import { Injectable } from '@angular/core';
import {GenericService} from "../generic.service";
import {Project} from "../../models/Project";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import { Milestone } from 'src/app/models/Milestone';
import {Observable, throwError} from "rxjs";
import {catchError, map, retry} from "rxjs/operators";
import {Member} from "../../models/Member";

@Injectable({
  providedIn: 'root'
})
export class ProjectService extends GenericService<Project> {

  constructor(http: HttpClient) {
      super(http);
      this.url =
          environment.backendApi.url +
          environment.backendApi.basePath +
          environment.backendApi.paths.project;

  }

  parseDates(o: Project) {
      super.parseDates(o);
      o.endDate = new Date(o.endDate);
      o.startDate = new Date(o.startDate);
  }

  getMilestones(id: number) : Observable<Milestone[]> {
    return this.http.get<Milestone[]>(this.url + '/' + id + '/milestones').pipe(
      map((data: Milestone[]) => {
        data.forEach(o => {
          o.endDate = new Date(o.endDate);
        });
        return data;
      }),
      retry(3),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  getMembers(id: number): Observable<Member[]> {
      return this.http.get<Member[]>(this.url + '/' + id + '/members').pipe(
          map((data: Member[]) => {
              return data;
          }),
          retry(3), // retry a failed request up to 3 times
          catchError(error => {
              console.log(error);
              return throwError(error);
          })
      );
  }

  addMember(projectId:number, member: Member){
      return this.http.post<Member>(this.url + '/' + projectId + '/members/', member).pipe(
          map((data: Member) => {
              return data;
          }),
          retry(3), // retry a failed request up to 3 times
          catchError(error => {
              console.log(error);
              return throwError(error);
          })
      );
  }

  removeMember(projectId:number, employeeId: number){
      return this.http.delete<Member[]>(this.url + '/' + projectId + '/members/' + employeeId).pipe(
          map((data: Member[]) => {
              return data;
          }),
          retry(3), // retry a failed request up to 3 times
          catchError(error => {
              console.log(error);
              return throwError(error);
          })
      );
  }


}
