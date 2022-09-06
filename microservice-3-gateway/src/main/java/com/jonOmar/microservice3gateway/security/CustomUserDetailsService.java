package com.jonOmar.microservice3gateway.security;
/*PASO 4: prev:service/UserServiceImpl

* - Implementar la interfaz UserDetailsService para modificar los parametros que
* tendra el usuario tras la autenticacion.
* - Metodo loadUserByUsername proveniente de la interfaz UserDetailsService, mapea
* al usuario con las propiedades requeridas para manipularlas mas adelante del proyecto
* en procesos de autorizacion.
* - Inyeccion de service/UserService para creacion e inyeccion de objeto User en propiedad
* UserDetailsService empleando el metodo de busqueda por username
* - Reconocimiento de autoridades del usuario mediante la busqueda de las propiedades enum
* del usuario a autenticar mediante parseo de cadenas a propiedades reconocidas por Spring
* mediante uso de Enums empleando el metodo estatico de SecurityUtils.convertToAuthority
* ubicado en utils/SecurityUtils
* - Pasar a PASO 5 en utils/SecurityUtils */

/*PASO 7: prev:security/UserPrincipal
*
* - Una vez definida la personalizacion del objeto UserDetails que se empleara durante
* la aplicacion se inicializa partiendo con el metodo loadUserByUsername que se encargara
* de buscar al usuario que intente una autenticacion y posteriormente devolvera una instancia
* de UserDetails personalizando con los parametros agregados en el anterior paso: security/UserPrincipal
* instanciando y apoyandonos del metodo estatico utils/SecurityUtils::converToAuthority y el servicio
* UserService para la busqueda del objeto usuario a partir del username capturado en el proceso
* de autenticacion
* - Pasar a PASO 8 en security/SecurityConfig*/

import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.service.UserService;
import com.jonOmar.microservice3gateway.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /*Para buscar un usuario por username*/
    @Autowired
    private UserService userService;

    /*El metodo por defecto proviene de la interfaz UserDetailsService y ayuda a la
    * busqueda del usuario a autenticar para retornar ciertas propiedades al momento
    * de la manipulacion de datos*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*Busqueda de usuario para pasar como instancia de UserDetailsService*/
        User user = userService.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("El usuario no fue encontrado: "+username));

        /*Authorities para emplear en procesos de autorizacion tras una autenticacion*/
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));

        return UserPrincipal.builder()
                .user(user)
                .id(user.getId())
                .username(username)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
