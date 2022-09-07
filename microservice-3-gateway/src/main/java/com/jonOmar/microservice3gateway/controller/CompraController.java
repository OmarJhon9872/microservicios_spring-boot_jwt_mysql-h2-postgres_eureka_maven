package com.jonOmar.microservice3gateway.controller;
/*PASO 24: prev:request/CompraServiceRequest
*
* - Se definen los 2 metodos del microservicio compra para poder interactuar
* desde el endpoint gateway/compra
* */

import com.jonOmar.microservice3gateway.request.CompraServiceRequest;
import com.jonOmar.microservice3gateway.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/compra")
public class CompraController {

    @Autowired
    private CompraServiceRequest compraServiceRequest;

    @PostMapping
    public ResponseEntity<?> saveCompra(@RequestBody Object compra){
        return new ResponseEntity<>(
            compraServiceRequest.saveCompra(compra),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllComprasOfUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(
            compraServiceRequest.getAllComprasOfUser(userPrincipal.getId())
        );
    }
}
