package com.jon.microservice1inmuebles.service;

import com.jon.microservice1inmuebles.model.Inmueble;
import com.jon.microservice1inmuebles.repository.InmuebleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InmuebleServiceImp implements InmuebleService{

    /*Remplazo de @Autowired - private InmuebleRepository in; dependency injection*/
    private final InmuebleRepository inmuebleRepository;
    public InmuebleServiceImp(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = inmuebleRepository;
    }
    /*######################################################*/

    @Override
    public Inmueble saveInmueble(Inmueble inmueble){
        inmueble.setFechaCreacion(LocalDateTime.now());
        return inmuebleRepository.save(inmueble);
    }

    @Override
    public void deleteInmueble(Long inmuebleId){
        inmuebleRepository.deleteById(inmuebleId);
    }

    @Override
    public List<Inmueble> findAllInmuebles(){
        return inmuebleRepository.findAll();
    }
}
