import {Injectable} from "@angular/core";
import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "./authenticate.service";

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(private router: Router, private auth: AuthService) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let path: string = next.url[0] ? next.url[0].path : "";
        if ((path === "login") && this.isAuthenticated()) {
            this.router.navigate(["/"]);
            return false;
        } else if ((path === "login") && (!this.isAuthenticated())) {
            return true;
        } else if ((path !== "login") && (!this.isAuthenticated())) {
            this.router.navigate(["/login"]);
            return true;
        } else {
            return true;
        }
    }

    isAuthenticated(): boolean {
        return this.auth.isAuthenticated() ? true : false;
    }
}
