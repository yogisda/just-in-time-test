import {ApiObject} from "./ApiObject";
import {Employee} from "./Employee";
import { Milestone } from './Milestone';

export class TimeBooking extends ApiObject {
    startTime: Date;
    endTime: Date;
    comments: String;
    employee: Employee;
    milestone: Milestone;

    parseDates(): void {
        super.parseDates();
        this.startTime = new Date(this.startTime);
        this.endTime = new Date(this.endTime);
    }
}
