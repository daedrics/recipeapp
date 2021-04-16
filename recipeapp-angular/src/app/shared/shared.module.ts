import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {FiltersComponent} from './filters/filters.component';
import {NavbarComponent} from './navbar/navbar.component';
import {PaginationComponent} from './pagination/pagination.component';
import {TableComponent} from './table/table.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from '../app-routing.module';
import { RouterModule } from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {TimeAgoModule} from './time-ago.module';
import {ModalComponent} from './modal/modal.component';

@NgModule({
    imports: [
        CommonModule, RouterModule, FormsModule, ReactiveFormsModule, AppRoutingModule, NgbModule, SweetAlert2Module, TimeAgoModule
    ],
    declarations: [FiltersComponent, NavbarComponent, PaginationComponent, TableComponent, ModalComponent
    ],
    exports: [FiltersComponent, NavbarComponent, PaginationComponent, TableComponent, CommonModule, ModalComponent
    ]
})
export class SharedModule {
}
