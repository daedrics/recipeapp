import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {ModalService} from '../../services/modal.service';


@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.sass']
})
export class SettingsComponent implements OnInit {

    user: any;
    updateForm: FormGroup;
    submitted = false;

    constructor(private authService: AuthenticationService,
                private formBuilder: FormBuilder,
                private toastr: ToastrService,
                private modalService: ModalService) {
    }

    ngOnInit() {
        this.updateForm = this.formBuilder.group({
            email: ['', [Validators.required]],
            name: ['', [Validators.required]],
            surname: ['', [Validators.required]],
        });

        this.authService.getProfile().subscribe(user => {
            this.user = user;
            this.updateForm.patchValue({
                email: this.user.email,
                name: this.user.name,
                surname: this.user.surname
            });
        });
    }

    get f() {
        return this.updateForm.controls;
    }

    onSubmit() {
        this.submitted = true;

        if (this.updateForm.invalid) {
            return;
        }
        this.user = Object.assign({}, this.updateForm.value);
        this.authService.updateProfile(this.user).subscribe(data => {
            this.toastr.success('Profile updated!', 'Success');
        });
    }

    changePass() {
        this.modalService.open({open: true});
    }

}
