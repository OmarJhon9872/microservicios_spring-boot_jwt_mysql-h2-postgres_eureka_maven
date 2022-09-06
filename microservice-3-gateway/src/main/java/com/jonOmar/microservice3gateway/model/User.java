package com.jonOmar.microservice3gateway.model;
/*PASO 1:
* - Creacion de la instancia de User
* - Enum de Role implementando 2 retornos, USER, ADMIN
* - Pasar a repository/UserRepository en PASO 2*/

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 255)
    public String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    /*Emumerated restringe los valores a la propiedad, en este caso en tipo cadena
    * en base al Emun especificado que trae 2 valores, USER o ADMIN*/
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Transient
    private String token;
}
