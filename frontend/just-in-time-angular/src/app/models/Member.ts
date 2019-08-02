import { ApiObject } from './ApiObject';
import {Employee} from "./Employee";
import {Project} from "./Project";
import {Role} from "./Role";

export class Member {
    employee:Employee;
    project: Project | number;
    role: Role | number;
    capacity: number;
}
