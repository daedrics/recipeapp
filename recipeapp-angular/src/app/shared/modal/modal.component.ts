import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../services/modal.service";
import {Modal} from "../../models/Modal";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {PasswordModel} from "../../models/PasswordModel";
import {ToastrService} from "ngx-toastr";


@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.sass']
})
export class ModalComponent implements OnInit {

    showModal = false;
    modal: Modal;

    changePasswordForm: FormGroup;
    submitted = false;
    passwordModel: PasswordModel;

    constructor(private modalService: ModalService,
                private formBuilder: FormBuilder,
                private authService: AuthenticationService,
                private toastr: ToastrService) {

    }

    ngOnInit() {
        this.modalService.changed.subscribe((modal: Modal) => {
            console.log(modal);
            this.checkModal(modal.open);
            this.modal = modal;

        });

        this.changePasswordForm = this.formBuilder.group({
            oldPassword: ['', Validators.required],
            newPassword: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        }, {validator: this.checkPasswords});
    }

    private checkModal(open: boolean) {
        if (open) {
            this.showModal = true;
            this.addOverlay(this.showModal);
        } else {
            this.close();
        }
    }

    close() {
        this.showModal = false;
        this.addOverlay(this.showModal);
    }

    private addOverlay(opened) {
        const body = document.getElementsByTagName('body')[0];
        if (opened) {
            body.classList.add('modal-open');
        }
        else {
            body.classList.remove('modal-open');
        }
    }

    onSubmit() {
        this.submitted = true;

        if (this.changePasswordForm.invalid) {
            return;
        }

        this.passwordModel = Object.assign({}, this.changePasswordForm.value);
        this.authService.changePassword(this.passwordModel).subscribe(
            () => {
                this.toastr.success("Please log in again", 'Password changed');
                this.authService.logout();
            },
            error => {
                this.toastr.error(error.message, "Error");
            }
        )

    }

    checkPasswords(group: FormGroup) {
        let pass = group.controls.newPassword.value;
        let confirmPass = group.controls.confirmPassword.value;
        return pass === confirmPass ? null : {notSame: true}
    }

    get f() {
        return this.changePasswordForm.controls;
    }
}
