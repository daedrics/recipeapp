package com.recipeapp.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.recipeapp.security.exception.InvalidTokenException;
import com.recipeapp.security.jwt.JwtProvider;
import com.recipeapp.security.service.UserDetailsServiceImpl;
import com.recipeapp.service.UserServiceImpl;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_SCHEMA = "Bearer";
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = validateRequestAndGetToken(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);
                System.out.println(username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            LOGGER.error("Internal authentication service exception", e);
            authenticationEntryPoint.commence(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);

    }

    private String validateRequestAndGetToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            throw new InvalidTokenException("Authorization header not found");
        }
        if (!token.contains(AUTHORIZATION_SCHEMA)) {
            throw new InvalidTokenException("Authorization schema not found");
        }
        token = token.substring(AUTHORIZATION_SCHEMA.length()).trim();
        return token;
    }
}

