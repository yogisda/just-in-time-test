import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from "../components/main/main.component";
import { OverviewComponent } from "../components/overview/overview.component";
import { LoginComponent } from "../components/login/login.component";
import { AuthGuard } from '../services/auth/auth.guard';
import { LogoutComponent } from '../components/logout/logout.component';
import {ProjectDetailComponent} from "../components/project-detail/project-detail.component";
import {ProfileComponent} from "../components/profile/profile.component";
import {MilestoneDetailComponent} from "../components/milestone-detail/milestone-detail.component";
import {TimeBookingDetailComponent} from "../components/time-booking-detail/time-booking-detail.component";
import {CustomerDetailComponent} from "../components/customer-detail/customer-detail.component";

const routes: Routes = [
  { path: '', redirectTo: '/main/overview', pathMatch: 'full' },
  {
    path: 'main', component: MainComponent, canActivate: [AuthGuard], children: [
      { path: '', redirectTo: 'overview', pathMatch: 'prefix' },
      { path: 'overview', component: OverviewComponent },
      { path: 'project/:id', component: ProjectDetailComponent},
      { path: 'project', component: ProjectDetailComponent},
      { path: 'profile', component: ProfileComponent},
      { path: 'timebooking/:id', component: TimeBookingDetailComponent},
      { path: 'timebooking', component: TimeBookingDetailComponent},
      { path: 'milestone/:id', component: MilestoneDetailComponent},
      { path: 'milestone', component: MilestoneDetailComponent},
      { path: 'customer/:id', component: CustomerDetailComponent},
      { path: 'customer', component: CustomerDetailComponent},

    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
