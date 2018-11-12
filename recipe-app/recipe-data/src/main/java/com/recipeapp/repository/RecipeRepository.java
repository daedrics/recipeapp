package com.recipeapp.repository;

import java.util.List;

import com.recipeapp.entity.RecipeEntity;

public interface RecipeRepository {

	RecipeEntity save(RecipeEntity recipe);

	RecipeEntity find(int id);

	RecipeEntity update(RecipeEntity recipe);

	void delete(RecipeEntity recipe);

	List<RecipeEntity> latestPublished();

	int userTotalRecipes(int userId, String name);

	List<RecipeEntity> searchRecipesByName(String name);

	List<RecipeEntity> userRecipes(Integer userId, Integer pageNumber, Integer pageSize, String name);

}
