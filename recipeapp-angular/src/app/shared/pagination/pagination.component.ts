import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';

const CHUNK = 2;

@Component({
    selector: 'app-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.sass']
})
export class PaginationComponent implements OnInit {

    currentPage = 1;
    currentChunk = CHUNK;
    left = false;
    right = false;


    @Input() totalPages;
    @Output() changePage: EventEmitter<any> = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
        if (this.totalPages.length > CHUNK) {
            this.right = true;
        }
    }

    next() {
        this.currentPage += 1;
        if (this.currentPage % CHUNK == 1) {
            this.currentChunk += CHUNK;
        }
        this.checkRightLeft();
        this.changePage.emit(this.currentPage);
    }

    previous() {
        this.currentPage -= 1;
        if (this.currentPage % CHUNK == 0) {
            this.currentChunk -= CHUNK;
        }

        this.checkRightLeft();
        this.changePage.emit(this.currentPage);
    }

    goToPage(page) {
        this.currentPage = page;
        this.changePage.emit(this.currentPage);
    }

    checkRightLeft() {
        if (this.currentPage > CHUNK) {
            this.right = false;
            this.left = true;
        }

        if (this.currentPage <= CHUNK) {
            this.right = true;
            this.left = false;
        }
    }

    checkPage(page): boolean {
        return (page + 1) <= this.currentChunk && (page + 1) > (this.currentChunk - CHUNK)
    }
}
