import {Component, Input, OnInit} from '@angular/core';
import {Employee} from "../../models/Employee";
import {Milestone} from "../../models/Milestone";
import {Project} from "../../models/Project";

@Component({
    selector: 'jit-fab',
    templateUrl: './fab.component.html',
    styleUrls: ['./fab.component.scss']
})
export class FabComponent implements OnInit {

    @Input()
    milestoneId:number;

    @Input()
    employeeId: number;

    @Input()
    projectId: number;


    constructor() {
    }

    ngOnInit() {
    }



}
