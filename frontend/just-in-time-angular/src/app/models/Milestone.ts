import { ApiObject } from './ApiObject';
import { Project } from './Project';

export class Milestone extends ApiObject {
    endDate:Date;
	name:String;
	description:String;
	estimatedHours:number;
	project:Project | number;

    parseDates() {
        super.parseDates();
        this.endDate = new Date(this.endDate);
    }
}
