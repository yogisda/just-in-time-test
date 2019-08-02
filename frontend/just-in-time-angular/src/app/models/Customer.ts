import { ApiObject } from './ApiObject';

export class Customer extends ApiObject {
  id:number;
  name:string;
  street:string;
  zipcode:string;
  city:string;
}
