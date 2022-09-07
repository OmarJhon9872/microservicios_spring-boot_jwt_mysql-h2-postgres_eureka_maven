package com.jonOmar.microservice3gateway.service;
/*PASO 13: prev:security/jwt/JwtAuthorizationFilter
*
* - Para la implementacion de autenticacion del usuario se empleada
* un campo @Transient String token en el modelo "User" como medio de almacenamiento
* temporal
* - Cuando la autenticacion se efectua se devuelve al usuario con el token
* almacenado de forma temporal
* - Pasamos al PASO 14 controller/AuthenticationController */

import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.security.UserPrincipal;
import com.jonOmar.microservice3gateway.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User signInAndReturnJWT(User signInrequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInrequest.getUsername(), signInrequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();

        /*Se almacenara de manera temporal gracias al metodo Transient del modelo */
        signInUser.setToken(jwt);

        return signInUser;
    }
}
