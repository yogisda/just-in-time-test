import { ApiObject } from './ApiObject';
import {Employee} from "./Employee";
import {Project} from "./Project";

export class Role extends ApiObject {
    id:number;
    description: string;
}
