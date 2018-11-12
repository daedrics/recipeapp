package com.recipeapp.exception;

public class RecipeAppAccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -4026088133989867416L;

	public RecipeAppAccessDeniedException() {
		super("Access Denied");
	}

}
