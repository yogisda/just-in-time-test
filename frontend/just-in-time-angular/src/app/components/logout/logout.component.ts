import { Component, OnInit } from "@angular/core";
import { AuthService } from "src/app/services/auth/authenticate.service";
import { Router } from "@angular/router";

@Component({
  selector: "jit-logout",
  templateUrl: "./logout.component.html",
  styleUrls: ["./logout.component.scss"]
})
export class LogoutComponent implements OnInit {


  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.authService.logout();
  }
}
