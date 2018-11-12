import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SignUpModel} from "../../models/SignUpModel";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.sass']
})
export class SignUpComponent implements OnInit {

    signUpForm: FormGroup;
    submitted = false;
    signUpModel: SignUpModel;

    constructor(private formBuilder: FormBuilder, private authService: AuthenticationService, private router: Router, private toastr: ToastrService) {
    }

    ngOnInit() {
        if (this.authService.isLoggedIn()) {
            this.router.navigate(["/home"]);
        }
        this.signUpForm = this.formBuilder.group({
            username: ["", Validators.required],
            password: ["", Validators.required],
            name: ["", Validators.required],
            surname: ["", Validators.required],
            email: ["", Validators.required],
            gender: [null, Validators.required]
        });
    }

    onSubmit() {
        this.submitted = true;
        if (this.signUpForm.invalid) {
            return;
        }

        this.signUpModel = Object.assign({}, this.signUpForm.value);

        this.authService.signUp(this.signUpModel).subscribe(user => {
            this.toastr.success("User created successfully", "Success");
            this.router.navigate(["/login"]);
        }, error => {
            this.toastr.error(error.message, "Error!");
        });
    }

    get f() {
        return this.signUpForm.controls;
    }

}
