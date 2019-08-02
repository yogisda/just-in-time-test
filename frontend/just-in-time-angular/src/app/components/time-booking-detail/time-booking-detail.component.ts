import {Component, OnInit} from '@angular/core';
import {EmployeeService} from "../../services/employee/employee.service";
import {SaveService} from "../../services/save/save.service";
import {ProjectService} from "../../services/project/project.service";
import {ActivatedRoute} from "@angular/router";
import {Project} from "../../models/Project";
import {Employee} from "../../models/Employee";
import {TimeBooking} from "../../models/TimeBooking";
import {TimeBookingService} from "../../services/timebooking/timebooking.service";
import {MatDatepickerInputEvent} from "@angular/material";
import { Milestone } from '../../models/Milestone';

@Component({
    selector: 'jit-time-booking-detail',
    templateUrl: './time-booking-detail.component.html',
    styleUrls: ['./time-booking-detail.component.scss']
})
export class TimeBookingDetailComponent implements OnInit {

    timeBooking: TimeBooking;
    projects: Project[];
    milestones: Milestone[];

    selectedProject: number;
    startTime: string = "00:00";
    endTime: string = "00:00";


    constructor(private route: ActivatedRoute,
                private projectService: ProjectService,
                private saveService: SaveService,
                private timeBookingService: TimeBookingService,
                private employeeService: EmployeeService) {
    }


    ngOnInit() {
        this.getData();
    }


    setDirty(): void {
        this.saveService.setSaved(false);
    }

    saveChanges(): void {
        if (this.timeBooking.id) {
            this.timeBookingService.update(this.timeBooking).subscribe((t) => {
                console.log(this.timeBooking.startTime.toISOString())
                this.timeBooking = t;
                console.log(t.startTime.toISOString());
                this.saveService.setSaved(true);
                this.loadDates();
            })
        } else {
            this.timeBookingService.create(this.timeBooking).subscribe((t) => {
                this.timeBooking = t;
                window.history.replaceState({}, '',`/main/timebooking/${t.id}`);
                this.saveService.setSaved(true);
                this.loadDates();
            })
        }
    }

    setDate(date: Date, event: MatDatepickerInputEvent<Date>): void {
        date.setFullYear(event.value.getFullYear(), event.value.getMonth(), event.value.getDate());
        this.setDirty();
    }

    loadDates() {
        this.startTime = this.timeBooking.startTime.toLocaleTimeString('de-DE',{'hour':'2-digit','minute':'2-digit'})
        this.endTime = this.timeBooking.endTime.toLocaleTimeString('de-DE',{'hour':'2-digit','minute':'2-digit'}) 
    }

    getData() {
        const id: number = +this.route.snapshot.paramMap.get('id');
        if (id !== 0) {
            this.timeBookingService.get(id).subscribe((t) => {
                this.timeBooking = t;
                this.selectedProject = this.timeBooking.milestone.project as number;
                this.onProjectSelected(this.selectedProject);
                this.loadDates();
            });
        } else {
            let timeBooking = new TimeBooking();
            timeBooking.employee = new Employee();
            timeBooking.milestone = new Milestone();
            this.timeBooking = timeBooking;
            this.employeeService.currentEmployee.subscribe((employee) => {
                this.timeBooking.employee.id = employee.id;
            });
            this.timeBooking.startTime = new Date();
            this.timeBooking.endTime = new Date();
        }

        this.projectService.getAll().subscribe((projects) => {
            this.projects = projects;
        });
    }

    onProjectSelected(selected: number) {
        this.projectService.getMilestones(selected).subscribe((milestones) => {
            this.milestones = milestones;
        });
    }

    onTimeChanged(target: Date, input: string) {
        const parts: string[] = input.split(":");
        const hours: number = parseInt(parts[0]);
        const minutes: number = parseInt(parts[1]);

        target.setHours(hours);
        target.setMinutes(minutes);

        this.setDirty();
    }

}
