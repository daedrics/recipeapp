package com.recipeapp.security.jwt;

import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.recipeapp.security.exception.InvalidTokenException;
import com.recipeapp.security.jwt.JwtProvider;
import com.recipeapp.security.service.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@PropertySource(value = "classpath:application.properties")
public class JwtProvider {

    private static final Logger LOGGER = Logger.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validFor}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expirationDate.toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(jwtExpiration);
    }

    public boolean validateJwtToken(String authToken) throws InvalidTokenException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature -> Message: {} ", e);
            throw new InvalidTokenException("Invalid JWT signature");

        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token -> Message: {}", e);
            throw new InvalidTokenException("Invalid JWT token");

        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token -> Message: {}", e);
            throw new InvalidTokenException("Expired JWT token");

        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token -> Message: {}", e);
            throw new InvalidTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty -> Message: {}", e);
            throw new InvalidTokenException("JWT claims string is empty -> Message: {}");
        }

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}

