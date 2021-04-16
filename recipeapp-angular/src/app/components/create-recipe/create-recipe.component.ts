import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Recipe} from "../../models/Recipe";
import {Observable} from 'rxjs/index';
import {Image} from "../../models/Image";
import {AngularFireStorage} from "@angular/fire/storage";
import {RecipesService} from "../../services/recipes.service";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {finalize} from "rxjs/internal/operators";

@Component({
    selector: 'app-create-recipe',
    templateUrl: './create-recipe.component.html',
    styleUrls: ['./create-recipe.component.sass']
})
export class CreateRecipeComponent implements OnInit {

    submitted = false;
    createForm: FormGroup;
    time = {hour: 0, minute: 0};
    recipe: Recipe;

    // upload properties
    uploadPercent: Observable<number>;
    downloadURL: Observable<string>;
    image: Image = new Image();
    previewImage = null;

    constructor(private formBuilder: FormBuilder,
                private storage: AngularFireStorage,
                private recipeService: RecipesService,
                private toastr: ToastrService,
                private router: Router) {
    }

    ngOnInit() {
        this.createForm = this.formBuilder.group({
            name: ['', Validators.required],
            servings: [0, [Validators.required, Validators.min(1)]],
            calories: [0, [Validators.required, Validators.min(1)]],
            description: ['', Validators.required],
            published: [true],
        });
    }

    get f() {
        return this.createForm.controls;
    }

    calculatedTime() {
        return this.time.hour * 60 + this.time.minute;
    }

    onSubmit() {
        this.submitted = true;
        if (this.createForm.invalid) {
            return;
        }

        this.recipe = Object.assign({}, this.createForm.value);
        this.recipe.time = this.calculatedTime() * 60; //in server it is saved in seconds

        //check if a file has been loaded

        if (this.image.file === null) {
            this.toastr.error("Please select an image!", "Upload an image!");
            return;
        }

        //upload first the image then save the recipe

        if (this.image.file.type.split('/')[0] !== 'image') {
            this.toastr.error("Only images allowed!", "Check file type!");
            return;
        } else {
            const path = `recipes/${new Date().getTime()}_${this.image.fileName}`;
            const fileRef = this.storage.ref(path);
            const task = this.storage.upload(path, this.image.file);
            this.uploadPercent = task.percentageChanges();
            task.snapshotChanges().pipe(
                finalize(() => {
                    this.downloadURL = fileRef.getDownloadURL();

                    //get image url from firebase
                    this.downloadURL.subscribe(data => {
                        this.recipe.imageUrl = data;

                        // save recipe
                        this.recipeService.createRecipe(this.recipe).subscribe(recipe => {
                            this.toastr.success("Recipe created successfully!", "Success!");
                            this.router.navigate(['/recipe-details', recipe.id]);
                        });
                    })
                })
            ).subscribe();
        }
    }

    upload(event) {
        const reader = new FileReader();
        const file = event.target.files[0];
        reader.readAsDataURL(file);
        reader.onload = (e: any) => {
            this.previewImage = e.target.result;
        };
        this.image = {file: file, fileName: file.name};
    }

    reset() {
        this.time = {hour: 0, minute: 0};
        this.createForm.reset();
    }


}
