package com.jonOmar.microservice3gateway.controller;
/*PASO 21: prev:request/InmuebleServiceRequest
*
* - Se definen los metodos controller para poder acceder a la
* api del microservicio Inmueble empleando la interfaz servicio
* Inmueble definida en el paso anterior
* - Pasar a PASO 22 en security/SecurityConfig */


import com.jonOmar.microservice3gateway.request.InmuebleServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/inmueble")
public class InmuebleController {

    @Autowired
    private InmuebleServiceRequest inmuebleServiceRequest;

    @PostMapping
    public ResponseEntity<?> saveInmueble(@RequestBody Object inmueble){
        return new ResponseEntity<>(
                inmuebleServiceRequest.saveInmueble(inmueble),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{inmuebleId}")
    public ResponseEntity<?> deleteInmueble(@PathVariable("inmuebleId") Long inmuebleId){
        inmuebleServiceRequest.deleteInmueble(inmuebleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllInmuebles(){
        return ResponseEntity.ok(
                inmuebleServiceRequest.getAllInmuebles()
        );
    }
}
