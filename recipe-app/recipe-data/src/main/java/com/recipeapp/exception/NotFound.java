package com.recipeapp.exception;

public enum NotFound {

    USER("User not found"),RECIPE("Recipe not found");
	

    public String getMessage() {
        return message;
    }

    private String message;

    private NotFound(String message) {
        this.message = message;
    }
}
