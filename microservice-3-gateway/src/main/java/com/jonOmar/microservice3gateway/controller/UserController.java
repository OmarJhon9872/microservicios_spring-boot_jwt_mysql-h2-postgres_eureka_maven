package com.jonOmar.microservice3gateway.controller;
/*PASO 15: prev:controller/AuthenticationController
*
* - Tras creacion de un endpoint para cambio de role que recibe solo 2 tipos de parametros
* de acuerdo al enum definido podemos enviar por el momento 2 peticiones de tipo put:
* - http://localhost:5555/api/user/change/ADMIN
* - http://localhost:5555/api/user/change/USER
* - Pasar al PASO 16 en security/jwt/JwtProviderImpl*/


import com.jonOmar.microservice3gateway.model.Role;
import com.jonOmar.microservice3gateway.security.UserPrincipal;
import com.jonOmar.microservice3gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role){
        userService.changeRole(role, userPrincipal.getUsername());

        return ResponseEntity.ok(true);
    }
}
