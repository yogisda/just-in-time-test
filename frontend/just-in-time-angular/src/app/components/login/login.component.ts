import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth/authenticate.service';
import {Router} from '@angular/router';
import {EmployeeService} from 'src/app/services/employee/employee.service';

@Component({
    selector: "jit-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
    password: string;
    email: string;
    authenticated: boolean = false;

    loginError = false;

    constructor(private authService: AuthService, private router: Router, private employeeService: EmployeeService) {
    }

    ngOnInit() {
        console.log('Hi');
        this.authService.authenticated.subscribe(auth => {
            this.authenticated = auth;
            if (this.authenticated) {
                this.router.navigate(['/']);
            }
        });

        // if(Math.random() < 0.1){
        //     this.activateTroll();
        // }

    }

    login(): void {

        this.authService.login(this.email, this.password).subscribe(
            ret => {
                if (ret === true) {
                    this.employeeService.setEmployee(
                        this.authService.getToken().id
                    );
                    this.router.navigate(['/']);
                    console.log('Logged in');
                } else {
                    console.log('Fehler:', ret);
                    this.loginError = true;
                }
            },
            err => {
                this.loginError = true;
            }
        );
    }

    activateTroll(): void {
        let main = document.querySelector('main');
        let login = document.querySelector('mat-card') as HTMLScriptElement;
        let maxtop = document.documentElement.offsetHeight - login.offsetHeight;
        let maxleft = document.documentElement.offsetWidth - login.offsetWidth;
        login.style.zIndex = '10000';
        login.style.position = 'fixed';
        let troll = document.createElement('img');
        troll.setAttribute('src', '/assets/images/Trollface.svg');
        troll.style.height = '10rem';
        login.parentElement.appendChild(troll);
        troll.style.display = 'block';
        troll.style.opacity = '0';
        troll.style.transition = 'opacity 0.3s ease-in-out';
        login.style.transition = 'all 0.3s ease-in-out';
        troll.style.position = 'fixed';

        let childs = login.querySelectorAll('*');
        for (let i = 0; i < childs.length; i++) {
            const e = childs[i] as HTMLScriptElement;
            e.style.pointerEvents = 'none';
        }
        login.addEventListener('click', (e) => {
            e.preventDefault();

            let top = Math.random() * maxtop;
            let left = Math.random() * maxleft;

            troll.style.top = login.style.top;
            troll.style.left = login.style.left;
            troll.style.opacity = '1';

            login.style.top = top + 'px';
            login.style.left = left + 'px';

            setTimeout(() => {
                troll.style.opacity = '0';
            }, 1000);
        });

    }
}
