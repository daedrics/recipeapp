package com.recipeapp.service;

import java.util.List;

import com.recipeapp.dto.LazyDataModel;
import com.recipeapp.dto.recipe.RecipeCreateDto;
import com.recipeapp.dto.recipe.RecipeDetailDto;
import com.recipeapp.dto.recipe.RecipeDto;

public interface RecipeService {

	RecipeDto addRecipe(Integer loggedUserId, RecipeCreateDto recipePostDto);

	RecipeDetailDto findRecipe(Integer id);

	RecipeDto updateRecipe(Integer loggedUserId, Integer id, RecipeCreateDto recipePostDto);

	void deleteRecipe(Integer loggedUserId, Integer id);

	List<RecipeDto> latestPublished(String name);

	LazyDataModel userRecipes(Integer userId, Integer pageNumber, Integer pageSize,String name);
}
