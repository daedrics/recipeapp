package com.recipeapp.converter;

import com.recipeapp.converter.CustomConverter;
import com.recipeapp.converter.UserConverter;
import com.recipeapp.dto.recipe.RecipeCreateDto;
import com.recipeapp.dto.recipe.RecipeDetailDto;
import com.recipeapp.dto.recipe.RecipeDto;
import com.recipeapp.entity.RecipeEntity;

public class RecipeConverter implements CustomConverter<RecipeEntity, RecipeCreateDto, RecipeDto> {

    private static final UserConverter USER_CONVERTER = new UserConverter();


    public RecipeEntity convert(RecipeCreateDto recipePostDto) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setCalories(recipePostDto.getCalories());
        recipe.setName(recipePostDto.getName());
        recipe.setPublished(recipePostDto.isPublished());
        recipe.setServings(recipePostDto.getServings());
        recipe.setTime(recipePostDto.getTime());
        recipe.setDescription(recipePostDto.getDescription());
        recipe.setImageUrl(recipePostDto.getImageUrl());
        return recipe;
    }


    public RecipeDto toGetDto(RecipeEntity recipe) {
        RecipeDto toReturn = new RecipeDto();
        toReturn.setUser(USER_CONVERTER.toGetDto(recipe.getUser()));
        toReturn.setCalories(recipe.getCalories());
        toReturn.setName(recipe.getName());
        toReturn.setId(recipe.getId());
        toReturn.setPublishedOn(recipe.getPublishedOn());
        toReturn.setPublished(recipe.isPublished());
        toReturn.setImageUrl(recipe.getImageUrl());
        return toReturn;
    }

    public RecipeDetailDto toGetDetailDto(RecipeEntity recipe) {
        RecipeDetailDto toReturn = new RecipeDetailDto();
        toReturn.setId(recipe.getId());
        toReturn.setName(recipe.getName());
        toReturn.setCalories(recipe.getCalories());
        toReturn.setTime(recipe.getTime());
        toReturn.setUser(USER_CONVERTER.toGetDto(recipe.getUser()));
        toReturn.setDescription(recipe.getDescription());
        toReturn.setServings(recipe.getServings());
        toReturn.setCreatedOn(recipe.getCreatedOn());
        toReturn.setPublished(recipe.isPublished());
        toReturn.setPublishedOn(recipe.getPublishedOn());
        toReturn.setImageUrl(recipe.getImageUrl());
        return toReturn;
    }
}
