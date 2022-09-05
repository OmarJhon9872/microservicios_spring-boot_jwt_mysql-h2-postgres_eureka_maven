package com.jon.microservice1inmuebles.repository;

import com.jon.microservice1inmuebles.model.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InmuebleRepository extends JpaRepository<Inmueble, Long> {
}