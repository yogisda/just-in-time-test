import {Component, Input, OnInit} from '@angular/core';
import {EmployeeService} from "../../services/employee/employee.service";
import {Employee} from "../../models/Employee";
import {Project} from "../../models/Project";
import {TimeBooking} from "../../models/TimeBooking";

@Component({
    selector: 'jit-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
    employee: Employee;

    projects: Project[];
    timeBookings: TimeBooking[];

    constructor(private employeeService: EmployeeService) {
    }

    ngOnInit() {
        this.employeeService.currentEmployee.subscribe(
            e => {
                if (e.id) {
                    this.employee = e;
                    this.getData();
                }
            }
        );
    }

    getData(): void {
        this.employeeService.getTimebookings(this.employee.id).subscribe(t => {
            this.timeBookings = t;
        });
        this.employeeService.getProjects(this.employee.id).subscribe(p => {
            this.projects = p;
        })
    }

}
