import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginBody} from "../../models/LoginModel";
import {first} from "rxjs/operators";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.sass"]
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    submitted = false;
    loginBody: LoginBody;

    constructor(private authService: AuthenticationService,
                private router: Router,
                private formBuilder: FormBuilder,
                private toastr: ToastrService) {
    }

    ngOnInit() {
        if (this.authService.isLoggedIn()) {
            this.router.navigate(["/home"]);
        }
        this.loginForm = this.formBuilder.group({
            username: ["", Validators.required],
            password: ["", Validators.required]
        });
    }

    get f() {
        return this.loginForm.controls;
    }

    onSubmit() {
        this.submitted = true;
        if (this.loginForm.invalid) {
            return;
        }
        this.loginBody = Object.assign({}, this.loginForm.value);
        this.authService
            .login(this.loginBody)
            .pipe(first())
            .subscribe(
                data => {
                    console.log(data);
                    this.router.navigate(["/home"]);
                },
                error => {
                    console.log(error);
                    this.toastr.error(error.message, "Login failed");
                }
            );
    }
}
