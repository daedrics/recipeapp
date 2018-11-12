import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RecipesService} from "../../services/recipes.service";
import {Recipe} from "../../models/Recipe";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AngularFireStorage} from "@angular/fire/storage";
import {ToastrService} from "ngx-toastr";
import {Observable} from "rxjs/index";
import {finalize, tap} from "rxjs/internal/operators";
import {Image} from "../../models/Image";

@Component({
    selector: 'app-edit-recipe',
    templateUrl: './edit-recipe.component.html',
    styleUrls: ['./edit-recipe.component.sass']
})
export class EditRecipeComponent implements OnInit {

    submitted = false;
    editForm: FormGroup;
    time = {hour: 0, minute: 0};
    recipe: Observable<Recipe>;
    editRecipe: Recipe;
    recipeId;

    //upload properties
    uploadPercent: Observable<number>;
    downloadURL: Observable<string>;
    image: Image = new Image();
    previewImage = null;
    oldImageName: string;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private recipeService: RecipesService,
                private formBuilder: FormBuilder,
                private storage: AngularFireStorage,
                private toastr: ToastrService) {
    }

    ngOnInit() {
        this.editForm = this.formBuilder.group({
            name: ['', Validators.required],
            servings: [0, [Validators.required, Validators.min(1)]],
            calories: [0, [Validators.required, Validators.min(1)]],
            description: ['', Validators.required],
            published: [true],
        });
        this.recipeId = this.route.snapshot.paramMap.get("id");
        this.recipe = this.recipeService.getRecipe(this.recipeId).pipe(
            tap((recipe: Recipe) => {
                const imageUrl = recipe.imageUrl;
                this.editForm.patchValue(recipe);
                this.previewImage = imageUrl;
                this.oldImageName = imageUrl.substring(80, imageUrl.indexOf('?alt'));
                this.time = this.transformTime(recipe.time);
            })
        );
    }


    onSubmit() {
        this.submitted = true;
        if (this.editForm.invalid) {
            return;
        }

        this.editRecipe = Object.assign({}, this.editForm.value);
        this.editRecipe.time = this.calculatedTime() * 60; //in server it is saved in seconds

        //if the image is not changed
        if (this.image.file === null) {
            this.editRecipe.id = this.recipeId;
            this.editRecipe.imageUrl = this.previewImage;
            this.recipeService.updateRecipe(this.editRecipe).subscribe(recipe => {
                this.toastr.success("Recipe updated successfully!", "Success!");
                this.router.navigate(['/my-recipes']);
            });
        } else {

            //delete first image
            const oldImagePath = `recipes/${this.oldImageName}`;
            const oldImageRef = this.storage.ref(oldImagePath);

            oldImageRef.delete().subscribe(() => {

                //upload new image
                const path = `recipes/${new Date().getTime()}_${this.image.fileName}`;
                const fileRef = this.storage.ref(path);
                const task = this.storage.upload(path, this.image.file);
                this.uploadPercent = task.percentageChanges();
                task.snapshotChanges().pipe(
                    finalize(() => {

                        //after upload is completed update recipe

                        this.downloadURL = fileRef.getDownloadURL();

                        this.downloadURL.subscribe(data => {

                            this.editRecipe.id = this.recipeId;
                            this.editRecipe.imageUrl = data;
                            this.recipeService.updateRecipe(this.editRecipe).subscribe((recipe: Recipe) => {
                                this.toastr.success("Recipe updated successfully!", "Success!");
                                this.router.navigate(['/my-recipes']);
                            })
                        })
                    })
                ).subscribe();
            })


        }
    }


    get f() {
        return this.editForm.controls;
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

    transformTime(time) {
        let hours = time / 3600;
        let minutes = time % 3600;
        return {hour: hours, minute: minutes}
    }

    reset() {
        this.time = {hour: 0, minute: 0};
        this.editForm.reset();
    }

    calculatedTime() {
        return this.time.hour * 60 + this.time.minute;
    }

}
