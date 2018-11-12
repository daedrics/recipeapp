import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.sass']
})

export class AppComponent implements OnInit {

    loggedIn: boolean;

    constructor(private authService: AuthenticationService) {
    }

    ngOnInit() {
        this.loggedIn = this.authService.isLoggedIn();
        this.authService.getLoggedIn().subscribe(loggedIn => {
            this.loggedIn = loggedIn;
        });
    }


}
