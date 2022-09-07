package com.jonOmar.microservice3gateway.request;
/*PASO 20: prev:Microservice3GatewayApplication
*
* - Se define el mapa que el Api Gateway podra seguir tras un request
* al microservicio de Inmuebles definiendo los parametros en el decorador
* @FeignClient
* - Definimos los metodos que puede recibir este microservicio a llamar
* - Pasamos al PASO 21 en controller/InmuebleController*/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value         = "inmueble-service",
        path          = "/api/inmueble",
        //url           = "${inmueble.service.url}",
        configuration = FeignConfiguration.class
)
public interface InmuebleServiceRequest {

    @PostMapping
    Object saveInmueble(@RequestBody Object requestBody);

    @DeleteMapping("{inmuebleId}")
    void deleteInmueble(@PathVariable("inmuebleId") Long inmuebleId);

    @GetMapping
    List<Object> getAllInmuebles();
}
