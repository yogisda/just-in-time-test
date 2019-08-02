import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Employee} from 'src/app/models/Employee';
import {EmployeeService} from 'src/app/services/employee/employee.service';
import {Md5} from 'ts-md5/dist/md5';
import {MediaMatcher} from "@angular/cdk/layout";
import {SaveService} from "../../services/save/save.service";

@Component({
    selector: 'jit-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, OnDestroy {
    employee: Employee;
    gravatarHash: string;

    saved: boolean;
    private _mobileQueryListener: () => void;
    mobileQuery: MediaQueryList;


    constructor(private employeeService: EmployeeService,
                changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
                private saveService: SaveService) {
        this.mobileQuery = media.matchMedia('(max-width: 1000px)');
        this._mobileQueryListener = () => changeDetectorRef.detectChanges();
        this.mobileQuery.addListener(this._mobileQueryListener);
    }

    ngOnInit() {
        this.employeeService.currentEmployee.subscribe(
            e => {
                this.employee = e;
                if (this.employee.email) {
                    this.gravatarHash = Md5.hashStr(this.employee.email.toLowerCase()) as string;
                }
            }
        );
        this.saveService.saved.subscribe(c => {
            this.saved = c;
        })

    }


    ngOnDestroy(): void {
        this.mobileQuery.removeListener(this._mobileQueryListener);
    }


    closeNav() {

    }
}
