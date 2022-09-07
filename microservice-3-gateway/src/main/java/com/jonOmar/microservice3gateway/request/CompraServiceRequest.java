package com.jonOmar.microservice3gateway.request;
/*PASO 23: prev:security/SecurityConfig
*
* - Se definen los metodos del microservicio relacionado, en este caso Compras
* - Pasar a la implementacion de endpoints PASO 24 en controller/CompraController*/
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value         = "compra-service",
        path          = "/api/compra",
        //url           = "${compra.service.url}",
        configuration = FeignConfiguration.class
)
public interface CompraServiceRequest {

    @PostMapping
    Object saveCompra(@RequestBody Object requestBody);

    @GetMapping("{userId}")
    List<Object> getAllComprasOfUser(@PathVariable("userId") Long userId);
}
