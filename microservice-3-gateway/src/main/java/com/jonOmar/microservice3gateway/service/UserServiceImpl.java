package com.jonOmar.microservice3gateway.service;
/*PASO 3: prev:repository/UserRepository

* - Instancia de userRepository para manipulacion de data
* - Uso de Override para crear instancia de metodos en interfaz service/UserService
* - Metodo saveUser para creacion de usuario con parametros requeridos
* - Pasar a security/CustomUserDetailsService en PASO 4*/

import com.jonOmar.microservice3gateway.model.Role;
import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setFechaCreacion(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findAllByUsername(username);
    }

    /*Siempre que se implemente una sentencia SQL de la base de datos
    * con el metodo @Query se debera usar @Transactional para evitar errores,
    * lo que hara es empezar Transacciones en la base de datos*/
    @Transactional
    @Override
    public void changeRole(Role newRole, String username){
        userRepository.updateUserRole(username, newRole);
    }

}
