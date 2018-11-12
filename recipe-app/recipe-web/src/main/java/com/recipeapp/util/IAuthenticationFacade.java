package com.recipeapp.util;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

    Authentication getAuthentication();
}
