import {Injectable, Output, EventEmitter} from '@angular/core';
import {Modal} from "../models/Modal";

@Injectable({
    providedIn: 'root'
})
export class ModalService {

    @Output() changed: EventEmitter<any> = new EventEmitter();
    modal: Modal;

    constructor() {
    }

    open(modal: Modal) {
        this.changed.emit(modal);
    }

    close() {
        this.modal = {
            open: false
        };
        this.changed.emit(this.modal);
    }
}
