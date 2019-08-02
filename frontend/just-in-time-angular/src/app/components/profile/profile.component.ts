import { Component, OnInit } from '@angular/core';
import {Employee} from "../../models/Employee";
import {EmployeeService} from "../../services/employee/employee.service";
import {Md5} from "ts-md5";
import {SaveService} from "../../services/save/save.service";

@Component({
  selector: 'jit-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

    employee:Employee;
    gravatarHash:string;

  constructor(private  employeeService:EmployeeService, private saveService:SaveService) { }

  ngOnInit() {
      this.employeeService.currentEmployee.subscribe(
          e => {
              this.employee = e;
              if (this.employee.email) {
                  this.gravatarHash = Md5.hashStr(this.employee.email.toLowerCase()) as string;
              }
          }
      );
  }

  setDirty():void{
      this.saveService.setSaved(false);
  }

  saveChanges():void{
      this.employeeService.update(this.employee).subscribe((e)=>{
          this.employee = e;
          this.saveService.setSaved(true);
      })

  }

}
