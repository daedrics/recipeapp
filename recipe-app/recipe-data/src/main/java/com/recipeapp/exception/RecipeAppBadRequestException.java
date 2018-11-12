package com.recipeapp.exception;

public class RecipeAppBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -3501410652768142933L;

	public RecipeAppBadRequestException(String message) {
		super(message);
	}

}