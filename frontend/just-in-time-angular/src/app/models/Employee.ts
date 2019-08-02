import { ApiObject } from './ApiObject';
import {IApiObject} from "./IApiObject";

export class Employee extends ApiObject {
    id:number;
   name:string;
   email:string;
   hoursPerDay:number;
   // role:number;
}
