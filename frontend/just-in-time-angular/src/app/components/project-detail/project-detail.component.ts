import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../../services/project/project.service";
import {ActivatedRoute} from "@angular/router";
import {Project} from "../../models/Project";
import {SaveService} from "../../services/save/save.service";
import {MatDatepickerInputEvent, MatDialog, MatTableDataSource} from "@angular/material";
import {Customer} from "../../models/Customer";
import {CustomerService} from "../../services/customer/customer.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {EmployeeService} from "../../services/employee/employee.service";
import {Employee} from "../../models/Employee";
import {SelectionModel} from "@angular/cdk/collections";
import {Member} from "../../models/Member";
import {DialogAddMemberComponent} from "../dialog-add-member/dialog-add-member.component";
import {PermissionService} from '../../services/permission/permission.service';
import {Milestone} from '../../models/Milestone';


@Component({
    selector: 'jit-project-detail',
    templateUrl: './project-detail.component.html',
    styleUrls: ['./project-detail.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({height: '0px', minHeight: '0'})),
            state('expanded', style({height: '*'})),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
})
export class ProjectDetailComponent implements OnInit {

    project: Project;
    customers: Customer[];
    members: Member[] = [];
    canEdit: boolean = false;
    milestones: Milestone[];
    addMembers: Member[] = [];
    removeMembers: Member[] = [];


    columnsToDisplay = ['select', 'name', 'hoursPerDay', 'role'];
    memberData: MatTableDataSource<Member>;
    selection = new SelectionModel<Member>(true, []);

    constructor(private route: ActivatedRoute,
                private projectService: ProjectService,
                private saveService: SaveService,
                private employeeService: EmployeeService,
                private customerService: CustomerService,
                private permissionService: PermissionService,
                public dialog: MatDialog) {
    }


    ngOnInit() {
        this.getData();
    }

    setDirty(): void {
        this.saveService.setSaved(false);
    }

    saveChanges(): void {
        if (this.project.id) {
            this.projectService.update(this.project).subscribe((p) => {
                this.project = p;
                this.removeMembers.forEach((m)=>{
                    this.projectService.removeMember(this.project.id, m.employee.id).subscribe((mem)=>{
                        console.log(mem);
                    });
                });
                this.addMembers.forEach((m)=>{
                    this.projectService.addMember(this.project.id, m).subscribe((mem)=>{
                        console.log(mem);
                    })
                });
                this.saveService.setSaved(true);
            })
        } else {
            this.projectService.create(this.project).subscribe((p) => {
                this.project = p;
                window.history.replaceState({}, '',`/main/project/${p.id}`);
                this.removeMembers.forEach((m)=>{
                    this.projectService.removeMember(this.project.id, m.employee.id).subscribe((mem)=>{
                        console.log(mem);
                    });
                });
                this.addMembers.forEach((m)=>{
                    this.projectService.addMember(this.project.id, m).subscribe((mem)=>{
                        console.log(mem);
                    })
                });
                this.saveService.setSaved(true);
            })
        }
    }

    setDate(date, event: MatDatepickerInputEvent<Date>): void {
        date.setFullYear(event.value.getFullYear(), event.value.getMonth(), event.value.getDate());
        this.setDirty();
    }


    getData() {
        const id: number = +this.route.snapshot.paramMap.get('id');
        if (id !== 0) {
            this.projectService.get(id).subscribe((p) => {
                this.project = p;
                console.log(p);
                this.permissionService.hasWritePermission('project', p).subscribe((res) => {
                    this.canEdit = res;
                });
                this.projectService.getMilestones(p.id).subscribe((milestones) => {
                    this.milestones = milestones;
                });
            });
            this.projectService.getMembers(id).subscribe((e) => {
                this.members = e;
                console.log(this.members);
                this.memberData = new MatTableDataSource<Member>(this.members);
            })
        } else {
            this.project = new Project();
            this.canEdit = true;
        }

        this.customerService.getAll().subscribe((c) => {
            this.customers = c;
        });
        this.memberData = new MatTableDataSource<Member>(this.members);


    }


    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.memberData.data.length;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
            this.memberData.data.forEach(row => this.selection.select(row));
    }

    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: Member): string {
        if (!row) {
            return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
        }
        return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.employee.id}`;
    }

    applyFilter(filterValue: string) {
        this.memberData.filter = filterValue.trim().toLowerCase();
    }

    removeMember() {
        this.selection.selected.forEach((m) => {
            let memberIndex = this.members.indexOf(m);
            this.removeMembers.push(m);
            this.members.splice(memberIndex, 1);
        });
        this.memberData.data = this.members;
        this.selection = new SelectionModel<Member>(true, []);
    }

    addMember() {
        // TODO Empty return
        const dialogRef = this.dialog.open(DialogAddMemberComponent, {
            width: '30rem',
            data: {
                id: this.project.id
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result !== undefined) {
                this.members.push(result);
                this.addMembers.push(result);
                this.memberData.data = this.members;
                this.selection = new SelectionModel<Member>(true, []);
            }
        });
    }


}
