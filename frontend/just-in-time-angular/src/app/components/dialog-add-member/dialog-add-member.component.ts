import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Member} from "../../models/Member";
import {Employee} from "../../models/Employee";
import {Role} from "../../models/Role";
import {Project} from "../../models/Project";
import {MatTableDataSource} from "@angular/material";
import {EmployeeService} from "../../services/employee/employee.service";
import {RoleService} from "../../services/role/role.service";

@Component({
    selector: 'jit-dialog-add-member',
    templateUrl: './dialog-add-member.component.html',
    styleUrls: ['./dialog-add-member.component.scss']
})
export class DialogAddMemberComponent implements OnInit {

    employee: Employee;
    employees: Employee[];
    role: Role;
    roles: Role[];
    member: Member;


    constructor(
        public dialogRef: MatDialogRef<DialogAddMemberComponent>, @Inject(MAT_DIALOG_DATA) public data: { id: number },
        private employeeService: EmployeeService,
        private roleService: RoleService) {
    }

    ngOnInit() {
        this.getData()
        this.member = {employee: this.employee, role: this.role, project: this.data.id, capacity:1};
    }

    onNoClick(): void {
        this.member = null;
        this.dialogRef.close();
    }

    getData() {
        const id: number = this.data.id;

        this.employeeService.getAll().subscribe((e) => {
            this.employees = e;
        })

        this.roleService.getAll().subscribe((r) => {
            this.roles = r;
        })
    }

    setMember(){
        this.member.employee = this.employee;
        this.member.role = this.role;
    }


}
