import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {AuthenticationService} from "../../services/authentication.service";
import {Recipe} from "../../models/Recipe";
import {RecipesService} from "../../services/recipes.service";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: "app-table",
    templateUrl: "./table.component.html",
    styleUrls: ["./table.component.sass"]
})
export class TableComponent implements OnInit {

    @Input()
    items: Recipe[] = [];
    @Input()
    hasPagination: boolean;
    @Input()
    loadingItems: boolean;
    @Output()
    getItem: EventEmitter<any> = new EventEmitter();
    @Input()
    totalPages = [];
    loggedUser;
    @Input() publishedColumn: boolean;
    swal = {title: 'Are you sure?', type: 'question', showCancelButton: 'true'};

    constructor(private authService: AuthenticationService,
                private recipeService: RecipesService,
                private toastr: ToastrService) {
    }

    ngOnInit() {
        this.loggedUser = this.authService.loggedUser();
    }

    deleteItem(id) {
        console.log('deleting recipe with id' + id);
        this.recipeService.deleteRecipe(id).subscribe(
            () => {
                this.items.splice(this.items.findIndex(v => v.id === id), 1);
                this.toastr.success(`Deleted recipe with id ${id}`, 'Success');
            },
            error => {
                this.toastr.error(error.message, "Error");
            }
        );
    }

    getItems(page) {
        console.log(page);
        this.getItem.emit(page);
    }

    publishedClass(item) {
        return item.published ? 'text-success' : 'text-danger';
    }
}
