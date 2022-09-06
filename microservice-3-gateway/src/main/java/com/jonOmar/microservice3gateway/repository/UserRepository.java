package com.jonOmar.microservice3gateway.repository;
/*PASO 2: prev:model/User

* - Extender de JpaRepository definiendo las propiedades de la entidad User creada
* - Creacion de 2 metodos adicionales
* - Pasar a service/UserServiceImpl en PASO 3*/

import com.jonOmar.microservice3gateway.model.Role;
import com.jonOmar.microservice3gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Estructura de metodos findBy + nombreCampo
    Optional<User> findAllByUsername(String username);

    /*Como haremos una modificacion a la base de datos agregamos en decorador Modifying*/
    @Modifying
    @Query("update User set role=:role where username=:username")
    void updateUserRole(@Param("username") String username, @Param("role")Role role);
}
