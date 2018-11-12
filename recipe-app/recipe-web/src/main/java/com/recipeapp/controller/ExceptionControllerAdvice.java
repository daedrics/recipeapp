package com.recipeapp.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.recipeapp.exception.RecipeAppAccessDeniedException;
import com.recipeapp.exception.RecipeAppBadRequestException;
import com.recipeapp.exception.RecipeAppNotFoundException;
import com.recipeapp.util.Response;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ RecipeAppNotFoundException.class, BadCredentialsException.class,
			RecipeAppAccessDeniedException.class, RecipeAppBadRequestException.class })
	public ResponseEntity<Response> exceptionHandler(RuntimeException ex) {
		Response response = new Response();
		HttpStatus status;
		if (ex instanceof RecipeAppNotFoundException) {
			status = HttpStatus.NOT_FOUND;
		} else if (ex instanceof RecipeAppAccessDeniedException) {
			status = HttpStatus.FORBIDDEN;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		response.setCode(status.value());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<Response>(response, status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Response response = new Response();
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		response.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
		response.setMessage("Validation errors");
		response.setError(errors);
		return response(ex, request, headers, HttpStatus.UNPROCESSABLE_ENTITY, response);
	}

	private ResponseEntity<Object> response(Exception ex, WebRequest request, HttpHeaders headers, HttpStatus status,
			Response response) {
		return handleExceptionInternal(ex, response, headers, status, request);
	}

}
