import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Recipe} from "../../models/Recipe";
import {RecipesService} from "../../services/recipes.service";
import {NgxSpinnerService} from "ngx-spinner";
import {AuthenticationService} from "../../services/authentication.service";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: "app-recipe-details",
    templateUrl: "./recipe-details.component.html",
    styleUrls: ["./recipe-details.component.sass"]
})
export class RecipeDetailsComponent implements OnInit {
    recipe: Recipe;
    loggedUser;
    swal = {title: 'Are you sure?', type: 'question', showCancelButton: 'true'};

    constructor(private route: ActivatedRoute,
                private recipeService: RecipesService,
                private authService: AuthenticationService,
                private toastr: ToastrService,
                private router: Router) {
    }

    ngOnInit() {
        this.loggedUser = this.authService.loggedUser();
        let id = this.route.snapshot.paramMap.get("id");
        this.recipeService.getRecipe(id).subscribe(recipe => {
            this.recipe = recipe;
        });
    }

    deleteRecipe() {
        this.recipeService.deleteRecipe(this.recipe.id).subscribe(
            () => {
                this.toastr.success(`Deleted recipe with id ${this.recipe.id}`, 'Success');
                this.router.navigate(['/my-recipes']);

            },
            error => {
                this.toastr.error(error.message, "Error");
            }
        );
    }
}
