import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Employee} from "../../models/Employee";
import {MatSnackBar} from "@angular/material";

@Injectable({
    providedIn: 'root'
})
export class SaveService {

    private SavedSource = new BehaviorSubject(true);
    public saved = this.SavedSource.asObservable();

    constructor(private _snackBar: MatSnackBar) {

    }

    setSaved(unsaved: boolean): void {
        this.SavedSource.next(unsaved);
        if (unsaved) {
            this._snackBar.dismiss();
            this._snackBar.open("Änderungen gespeichert","OK",{duration: 1000});
        }
    }


}
