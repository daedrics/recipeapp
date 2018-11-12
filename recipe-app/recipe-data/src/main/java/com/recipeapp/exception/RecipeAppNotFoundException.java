package com.recipeapp.exception;

import com.recipeapp.exception.NotFound;

public class RecipeAppNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecipeAppNotFoundException(NotFound notFound) {
		super(notFound.getMessage());
	}

}
