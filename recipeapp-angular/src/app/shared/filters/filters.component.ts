import {Component, EventEmitter, OnInit, Output, Input} from '@angular/core';

@Component({
    selector: 'app-filters',
    templateUrl: './filters.component.html',
    styleUrls: ['./filters.component.sass']
})
export class FiltersComponent implements OnInit {

    @Input() title: string;
    timeout = null;
    @Output() searchText: EventEmitter<string> = new EventEmitter();

    constructor() {
    }

    ngOnInit() {

    }

    onSearchChange(text :string) {
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
            this.searchText.emit(text);
        }, 700)
    }
}
