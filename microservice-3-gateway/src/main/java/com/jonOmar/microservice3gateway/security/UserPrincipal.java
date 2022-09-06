package com.jonOmar.microservice3gateway.security;
/*PASO 6: prev:utils/SecurityUtils
*
* - Implementacion y edicion de UserDetails empleado en procesos
* de autorizacion tras autenticacion de usuarios, agregando
* parametros personalizados que se usaran durante la ejecucion
* de la aplicacion.
* - Esta clase sera un remplazo de la implementacion de Spring de
* UserDetails que contendra mas objetos inicializados en el siguiente
* paso
* - Regresar a security/CustomUserDetailsService en PASO 7*/

import com.jonOmar.microservice3gateway.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


/*Getter especifica a las nuevas propiedades que se agregaron adicionales*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
/*Builder(Build Data) encargado de inicializar los datos de la clase
* id, username, password, User user y authorities */
@Builder
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;

    /*Para evitar que se serialicen los datos que se devolveran en el request
    * se agregar el transient para ocultarlos pero tener acceso en caso de
    * implementar alguna operacion con ellos*/
    transient private String password;
    transient private User user;

    /*Por defecto Spring llama a un rol Authory por lo que especificamos el tipo
    * por defecto sera llamado cuando el metodo "getAuthorities" sea invocado*/
    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
