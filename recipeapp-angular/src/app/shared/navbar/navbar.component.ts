import {Component, OnInit, Input} from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.sass']
})
export class NavbarComponent implements OnInit {

    showClass = false;
    @Input() loggedIn;

    links = [
        {path: 'home', label: 'Home'},
        {path: 'my-recipes', label: 'My Recipes'},
        {path: 'settings', label: 'Settings'},
    ];

    constructor(private authService: AuthenticationService) {
    }

    ngOnInit() {

    }

    showMenu() {
        this.showClass = !this.showClass;
    }

    logOut() {
        console.log('clicked');
        this.authService.logout();
    }

}
