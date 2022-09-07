package com.jonOmar.microservice3gateway.security.jwt;

import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.security.UserPrincipal;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);

    /*#######################################################*/
    /*Tercera parte de implementacion en PASO 16*/
    String generateToken(User user);
}
