package com.recipeapp.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenException(String message) {
		super(message);
	}

}
