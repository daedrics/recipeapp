import {Component, OnInit} from "@angular/core";
import {RecipesService} from "../../services/recipes.service";
import {Recipe} from "../../models/Recipe";

@Component({
    selector: "app-home",
    templateUrl: "./home.component.html",
    styleUrls: ["./home.component.sass"]
})
export class HomeComponent implements OnInit {

    title: string;
    recipes: Recipe[];
    loading: boolean;

    constructor(private recipeService: RecipesService) {
    }

    ngOnInit() {
        this.loading = true;
        this.recipeService.getRecipes().subscribe(recipes => {
            setTimeout(() => {
                this.loading = false;
                this.recipes = recipes;
                this.title = `Published recipes (${this.recipes.length})`;
            }, 300);

        });
    }

    filterRecipes(searchText) {
        this.loading = true;
        this.recipes = null;
        this.recipeService.filterPublishedRecipes(searchText).subscribe(recipes => {
            setTimeout(() => {
                this.loading = false;
                this.recipes = recipes;
            }, 300);
        });

    }
}
