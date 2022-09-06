package com.jonOmar.microservice3gateway.utils;
/*PASO 5: prev:security/CustomUserDetailsService

* - Metodo estatico convertToAuthority retornando una estancia de
* SimpleGrantedAuthority, misma encargada de pasar una cadena de tipo Role
* a un objeto autoritativo para despues emplearlo en procesos de
* autorizacion tras una autenticacion
* - Pasar a security/UserPrincipal en PASO 6 */

/*PASO 10: prev:security/jwt/JwtProviderImpl
*
* - En SECCION VALIDACION TOKEN:
* - Definimos y especificamos si las peticiones del cliente contienen un header
* con el parametro "authorization" y si es de tipo Bearer, en caso de ser cierto
* obtenemos la cadena token procesada con el metodo extractAuthTokenFromRequest()
* para devolver a security/jwt/JwtProviderImpl
* - Continuar en PASO 11 en security/jwt/JwtProviderImpl */

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    public static final String ROLE_PREFIX = "ROLE_";

    /*############## SECCION VALIDACION TOKEN #########################*/
    /*Parte del PASO 10 para definicion de objetos a emplear para identificacion de token*/
    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_TYPE = "Bearer";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE + " ";
    public static String extractAuthTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTH_HEADER);

        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }
    /*#######################################################*/


    public static SimpleGrantedAuthority convertToAuthority(String role){
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role:ROLE_PREFIX+role;
        return new SimpleGrantedAuthority(formattedRole);
    }

}
