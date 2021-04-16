import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {MyRecipesComponent} from './components/my-recipes/my-recipes.component';
import {SettingsComponent} from './components/settings/settings.component';
import {RecipeDetailsComponent} from './components/recipe-details/recipe-details.component';
import {CreateRecipeComponent} from './components/create-recipe/create-recipe.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {EditRecipeComponent} from './components/edit-recipe/edit-recipe.component';

const routes: Routes = [
  {path: 'my-recipes', component: MyRecipesComponent, canActivate: [AuthGuard]},
  {path: 'create', component: CreateRecipeComponent, canActivate: [AuthGuard]},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'settings', component: SettingsComponent, canActivate: [AuthGuard]},
  {path: 'recipe-details/:id', component: RecipeDetailsComponent, canActivate: [AuthGuard]},
  {path: 'edit-recipe/:id', component: EditRecipeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
