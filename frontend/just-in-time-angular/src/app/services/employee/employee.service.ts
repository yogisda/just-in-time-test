import {Injectable} from '@angular/core';
import {GenericService} from '../generic.service';
import {Employee} from 'src/app/models/Employee';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {AuthService} from "../auth/authenticate.service";
import {catchError, map, retry} from "rxjs/operators";
import {Project} from "../../models/Project";
import {TimeBooking} from "../../models/TimeBooking";
import {Time} from "@angular/common";
import {ProjectService} from "../project/project.service";
import {TimeBookingService} from "../timebooking/timebooking.service";

@Injectable({
    providedIn: "root"
})
export class EmployeeService extends GenericService<Employee> {
    private employeeSource = new BehaviorSubject(new Employee());
    currentEmployee = this.employeeSource.asObservable();

    constructor(http: HttpClient, private authService: AuthService, private timeBookingService: TimeBookingService, private projectService: ProjectService) {
        super(http);

        this.url =
            environment.backendApi.url +
            environment.backendApi.basePath +
            environment.backendApi.paths.employee;

        // let e = new Employee();
        // e.email = "heinz@doofenschmirtz.evil";
        // e.name = "Dr. Heinz Doofenschmirtz";
        // this.employeeSource.next(e);
        if (this.authService.isAuthenticated()) {
            this.setEmployee(this.authService.getToken().id);
        }
    }


    setEmployee(id: number) {
        this.get(id).subscribe((e) => {
            this.employeeSource.next(e);
        })
    }


    getProjects(id: number, query: string = ''): Observable<Project[]> {
        return this.http.get<Project[]>(this.url + '/' + id + '/projects' + query).pipe(
            map((data: Project[]) => {
                data.forEach(o => {
                    this.projectService.parseDates(o);
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

    getTimebookings(id: number, query: string = ''): Observable<TimeBooking[]> {
        return this.http.get<TimeBooking[]>(this.url + '/' + id + '/timebookings' + query).pipe(
            map((data: TimeBooking[]) => {
                console.log(data.length);
                data.forEach((o: TimeBooking) => {
                    this.timeBookingService.parseDates(o);
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
}
