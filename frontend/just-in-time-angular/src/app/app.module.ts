import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppRoutingModule} from "./modules/app-routing.module";
import {AppComponent} from "./components/app/app.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MaterialModule} from "./modules/material.module";
import {AuthService} from './services/auth/authenticate.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { MAT_DATE_LOCALE } from "@angular/material/core";
import {RestInterceptor} from './RestInterceptor';
import {AuthGuard} from './services/auth/auth.guard';
import {FormsModule} from '@angular/forms';
import {LoginComponent} from './components/login/login.component';
import {MainComponent} from "./components/main/main.component";
import {OverviewComponent} from "./components/overview/overview.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {FabComponent} from './ui-elements/fab/fab.component';
import {ProjectDetailComponent} from './components/project-detail/project-detail.component';
import {ProfileComponent} from './components/profile/profile.component';
import {MilestoneDetailComponent} from './components/milestone-detail/milestone-detail.component';
import {CustomerDetailComponent} from './components/customer-detail/customer-detail.component';
import {TimeBookingDetailComponent} from './components/time-booking-detail/time-booking-detail.component';
import { DialogAddMemberComponent } from './components/dialog-add-member/dialog-add-member.component';
import { MilestoneComponent } from './components/milestone/milestone.component';


@NgModule({
    declarations: [AppComponent,
        LoginComponent,
        MainComponent,
        OverviewComponent,
        LogoutComponent,
        FabComponent,
        ProjectDetailComponent,
        ProfileComponent,
        MilestoneDetailComponent,
        CustomerDetailComponent,
        TimeBookingDetailComponent,
        DialogAddMemberComponent,
        MilestoneComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MaterialModule,
        FormsModule,
        HttpClientModule
    ],
    entryComponents:[
        DialogAddMemberComponent
    ],
    providers: [
        AuthService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: RestInterceptor,
            multi: true
        },
        {provide: MAT_DATE_LOCALE, useValue: "de-DE"},
        AuthGuard
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
