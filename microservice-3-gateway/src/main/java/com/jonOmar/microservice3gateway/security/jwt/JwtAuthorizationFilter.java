package com.jonOmar.microservice3gateway.security.jwt;
/*PASO 12: prev:security/jwt/JwtProviderImpl
*
* - Se implementara un filter para interceptar las peticiones de un cliente
* y realizar las validaciones correspondientes en este caso de autenticacion
* para procesar siguiente peticion
* - Se inyecta en @Bean en security/awt/JwtProviderImpl para que el autowired
* funcione en la instancia que se crea
* - Esta clase en inyectada como Bean en security/SecurityConfig para indicar
* parametros de autorizacion en metodo securityFilterChain()
* - Pasamos al PASO 13 en service/AuthenticationServiceImpl */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = jwtProvider.getAuthentication(request);

        /*Si es autenticacion valida colocara una autenticacion valida */
        if(authentication != null && jwtProvider.isTokenValid(request)){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        /*En caso de ser autenticacion no valida devolvera el request con el response generado de no autorizado*/
        filterChain.doFilter(request, response);
    }
}
