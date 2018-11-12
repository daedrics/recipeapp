import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import {ToastrModule} from "ngx-toastr";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NgxSpinnerModule} from "ngx-spinner";
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';
import {AngularFireModule} from "@angular/fire";
import {AngularFireStorageModule} from "@angular/fire/storage";
import {environment} from "../environments/environment";

import {AppRoutingModule} from "./app-routing.module";
import {SharedModule} from "./shared/shared.module";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {ErrorInterceptor} from "./interceptors/error.interceptor";

import {AppComponent} from "./app.component";
import {HomeComponent} from "./components/home/home.component";
import {MyRecipesComponent} from "./components/my-recipes/my-recipes.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {RecipeDetailsComponent} from "./components/recipe-details/recipe-details.component";
import {CreateRecipeComponent} from "./components/create-recipe/create-recipe.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {EditRecipeComponent} from './components/edit-recipe/edit-recipe.component';
import {TimeAgoModule} from "./shared/time-ago.module";

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        MyRecipesComponent,
        SettingsComponent,
        RecipeDetailsComponent,
        CreateRecipeComponent,
        LoginComponent,
        SignUpComponent,
        EditRecipeComponent
    ],
    imports: [
        BrowserAnimationsModule,
        SweetAlert2Module.forRoot(),
        ToastrModule.forRoot(),
        TimeAgoModule,
        SharedModule,
        NgxSpinnerModule,
        AngularFireModule.initializeApp(environment.firebase),
        AngularFireStorageModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgbModule.forRoot(),
        AppRoutingModule
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
       // {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true},
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
