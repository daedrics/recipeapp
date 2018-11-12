import {Component, OnInit} from '@angular/core';
import {RecipesService} from '../../services/recipes.service';
import {Recipe} from '../../models/Recipe';

@Component({
    selector: 'app-my-recipes',
    templateUrl: './my-recipes.component.html',
    styleUrls: ['./my-recipes.component.sass']
})
export class MyRecipesComponent implements OnInit {

    recipes: Recipe[];
    totalPages;
    title: string;
    loading: boolean;
    filterText = null;

    constructor(private recipeService: RecipesService) {
    }

    ngOnInit() {
        this.loading = true;
        this.recipeService.getMyRecipes(1).subscribe(recipes => {
            setTimeout(() => {
                this.loading = false;
                this.recipes = recipes.list;
                this.totalPages = Array(recipes.totalPageNumber).fill(0).map((x, i) => i);
                this.title = `My recipes (${this.recipes.length})`;
            }, 300);
        });
    }

    filterRecipes(searchText) {
        this.filterText = searchText;
        if (searchText === '') {
            this.filterText = null;
        }
        this.recipes = null;
        this.loading = true;
        this.recipeService.filterUserRecipes(searchText, 1).subscribe(recipes => {
            setTimeout(() => {
                this.loading = false;
                this.recipes = recipes.list;
                this.totalPages = Array(recipes.totalPageNumber).fill(0).map((x, i) => i);
            }, 300);
        });
    }

    getRecipes(page) {
        this.loading = true;
        this.recipes = null;
        if (this.filterText !== null) {
            this.recipeService.filterUserRecipes(this.filterText, page).subscribe(recipes => {
                setTimeout(() => {
                    this.loading = false;
                    this.recipes = recipes.list;
                }, 300);
            });
        } else {
            this.recipeService.getMyRecipes(page).subscribe(recipes => {
                setTimeout(() => {
                    this.loading = false;
                    this.recipes = recipes.list;
                }, 300);
            });
        }

    }
}
