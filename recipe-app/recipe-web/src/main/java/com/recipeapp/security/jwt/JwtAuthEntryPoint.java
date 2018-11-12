package com.recipeapp.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipeapp.security.exception.InvalidTokenException;
import com.recipeapp.util.Response;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper mapper;
	
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	Response errorResponse = new Response();
    	HttpStatus status;
    	Map<String,String> errorMessage = new HashMap<>();
    	if (authException instanceof InvalidTokenException) {
    		status = HttpStatus.UNAUTHORIZED;
    		errorMessage.put("message", authException.getMessage());
    		errorResponse.setError(errorMessage);
    		errorResponse.setMessage("InvalidTokenException");
    	} else {
    		status = HttpStatus.FORBIDDEN;
    		errorMessage.put("message", status.getReasonPhrase());
    		errorResponse.setError(errorMessage);
    		errorResponse.setMessage(authException.getMessage());
    	}
    	errorResponse.setCode(status.value());
    	response.setStatus(status.value());
    	response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	mapper.writeValue(response.getWriter(), errorResponse);
    }
}
