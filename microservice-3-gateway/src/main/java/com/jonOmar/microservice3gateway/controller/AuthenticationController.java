package com.jonOmar.microservice3gateway.controller;
/*PASO 14: prev:service/AuthenticationServiceImpl
*
* - Definimos 2 endpoints para el menejo de la autenticacion a partir de todos
* los pasos anteriores y poder regresar un auth token JWT con los datos del modelo usuario
* - Probamos los endpoints verificando las claves secret tengan la longitud correcta del
* algoritmo a emplear para evitar colision en la generacion del token, en este caso usando
* el algoritmo HS512 o SHA512 que por estandar establece que la longitud de la secret
* debera de ser de 64 caracteres-bytes
* HS512 ：HMAC-SHA-512， 512 bits (64 bytes)
* - Pasamos al PASO 15 en controller/UserController*/

import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.service.AuthenticationService;
import com.jonOmar.microservice3gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user){
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(
                userService.saveUser(user),
                HttpStatus.CREATED
        );
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user){
        return new ResponseEntity<>(
                authenticationService.signInAndReturnJWT(user),
                HttpStatus.OK
        );
    }
}
