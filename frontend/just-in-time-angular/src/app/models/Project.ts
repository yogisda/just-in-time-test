import {ApiObject} from "./ApiObject";
import {Customer} from "./Customer";
import {Member} from "./Member";

export class Project extends ApiObject {
    startDate: Date;
    endDate: Date;
    name: string;
    description: string;
    finished: boolean;
    estimatedHours: number;
    customer:Customer | number;
    // private milestones:Milestones[];
    members: Member[] | number[];


    parseDates() {
        super.parseDates();
        this.startDate = new Date(this.startDate);
        this.endDate = new Date(this.endDate);
    }

    constructor() {
        super();
        let d = new Date();
        d.setHours(0, 0, 0, 0);
        this.startDate = new Date(d);
        d.setDate(d.getDate() + 1);
        this.endDate = new Date(d);
        this.name = '';
        this.description = '';
        this.finished = false;
        this.estimatedHours = 0;
        this.customer = null;

    }
}
