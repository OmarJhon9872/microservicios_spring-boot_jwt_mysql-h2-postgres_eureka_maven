package com.jon.microservice1inmuebles.controller;

import com.jon.microservice1inmuebles.model.Inmueble;
import com.jon.microservice1inmuebles.service.InmuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
/*Para indicar que el recurso tendra acceso a los Object Mappers*/
@RequestMapping("api/inmueble")
public class InmuebleController {

    @Autowired
    private InmuebleService inmuebleService;

    /* POST localhost:3333/api/inmueble */
    @PostMapping
    public ResponseEntity<?> saveInmueble(@RequestBody Inmueble inmueble){
        return new ResponseEntity<>(
                inmuebleService.saveInmueble(inmueble),
                HttpStatus.CREATED
        );
    }

    /*Para acceder a la peticion se debera mandar un request de tipo delete con:
    * DELETE localhost:3333/api/inmueble/100 */
    @DeleteMapping("{inmuebleId}")
    public ResponseEntity<?> deleteInmueble(@PathVariable Long inmuebleId){
        inmuebleService.deleteInmueble(inmuebleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* GET localhost:3333/api/inmueble */
    @GetMapping
    public ResponseEntity<?> getAllInmueble(){
        return new ResponseEntity<>(
                inmuebleService.findAllInmuebles(),
                HttpStatus.OK
        );
    }
}
