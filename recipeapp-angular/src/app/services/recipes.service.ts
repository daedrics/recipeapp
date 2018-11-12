import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Recipe} from "../models/Recipe";

@Injectable({
    providedIn: "root"
})
export class RecipesService {

    url = environment.baseUrl;

    constructor(private http: HttpClient) {
    }

    getRecipes(): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`${this.url}/recipes`);
    }

    getRecipe(id): Observable<Recipe> {
        return this.http.get<Recipe>(`${this.url}/recipes/${id}`);
    }

    getMyRecipes(page): Observable<any> {
        return this.http.get<any>(`${this.url}/users/recipes/${page}/5`);
    }

    filterUserRecipes(name: string, page): Observable<any> {
        return this.http.get<any>(`${this.url}/users/recipes/${page}/5?name=${name}`);
    }

    createRecipe(recipe: Recipe): Observable<Recipe> {
        return this.http.post<Recipe>(`${this.url}/recipes`, recipe);
    }

    updateRecipe(recipe: Recipe): Observable<Recipe> {
        return this.http.put<Recipe>(`${this.url}/recipes/${recipe.id}`, recipe);
    }

    deleteRecipe(id: number) {
        return this.http.delete(`${this.url}/recipes/${id}`);
    }

    filterPublishedRecipes(name: string): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`${this.url}/recipes?name=${name}`);
    }
}
