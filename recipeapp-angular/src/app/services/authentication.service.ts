import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from '../../environments/environment';
import {map} from "rxjs/internal/operators";
import {Observable, Subject} from "rxjs";
import {Router} from "@angular/router";
import {LoginBody} from '../models/LoginModel';
import {SignUpModel} from "../models/SignUpModel";
import {PasswordModel} from "../models/PasswordModel";

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private loggedIn = new Subject<boolean>();

    constructor(private http: HttpClient, private router: Router) {
    }

    login(loginBody: LoginBody) {
        return this.http.post<any>(`${environment.baseUrl}/login`, loginBody)
            .pipe(map(user => {
                if (user && user.token) {
                    localStorage.setItem('user', JSON.stringify(user));
                    this.loggedIn.next(true);
                }
                return user;
            }));
    }


    signUp(signUpModel: SignUpModel): Observable<SignUpModel> {
        return this.http.post<SignUpModel>(`${environment.baseUrl}/users/signup`, signUpModel)
    }

    logout() {
        this.loggedIn.next(false);
        localStorage.removeItem('user');
        this.router.navigate(['/login']);
    }

    updateProfile(user) {
        return this.http.put(`${environment.baseUrl}/users`, user);
    }

    changePassword(passwordModel: PasswordModel) {
      return this.http.post(`${environment.baseUrl}/users/changePassword`, passwordModel);
    }

    getProfile() {
        return this.http.get(`${environment.baseUrl}/users`);
    }

    getLoggedIn() {
        return this.loggedIn.asObservable();
    }

    isLoggedIn() {
        let user = JSON.parse(localStorage.getItem('user'));
        if (user && user.token) {
            return true;
        } else
            return false;
    }

    loggedUser() {
        let user = JSON.parse(localStorage.getItem('user'));
        return user;
    }
}
