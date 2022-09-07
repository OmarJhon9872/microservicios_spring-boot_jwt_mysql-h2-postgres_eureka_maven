package com.jonOmar.microservice3gateway.security.jwt;
/*PASO 10: prev:application.properties
*
* - Se importan los valores declarados en archivo de propiedades en paso anterior
* para definir la llave de encriptacion y tiempo de vida de token
* - generateToken se envia a la interfaz que implementa JwtProvider
* - Definicion de permisos de usuario con cadena authorities
* - Construccion de token para cifrado de informacion
* - Pasar a PASO 11 en utils/SecurityUtils */

/*PASO 11: prev:utils/SecurityUtils
*
* - En extractClaims se extraen los claims-propiedades del token en caso de ser
* valido tras las operaciones
    Jwts.parserBuilder()   -> Llamamos al builder para comenzar verificacion
    .setSigningKey(key)    -> Indicamos la llave privada que se genera una linea previo
    .build()               -> Comenzamos el proceso
    .parseClaimsJwt(token) -> Validamos contra el token recibido por el cliente en request
    .getBody();            -> Obtenemos los claims
* - En getAuthentication() retornamos una instancia de UsernamePasswordAuthenticationToken,
* mismo que contendra la construccion del UserDetails que personalizamos en security/UserPrincipal,
* solo en caso de que el token sea validado correctamente.
* - Finalmente verificamos que el token sea valido en cuestion a expiracion con isTokenValid()
* - La composicion de un JWT token se divide en 3 partes:
    1 es la información del encabezado (Encabezado)
    2 es la carga útil (Carga útil)
    3 es la firma (Firma).
* - Pasar a PASO 12 en security/jwt/JwtAuthorizationFilter*/

/*PASO 16: prev:controller/UserController
*
* - Generamos el metodo generateToken(User user) que recibe una instancia de user y segun
* los datos recibidos creara un token para devolver al usuario tras la creacion de un nuevo
* usuario
* - Pasar a PASO 17 en: service/UserServiceImpl*/
import com.jonOmar.microservice3gateway.model.User;
import com.jonOmar.microservice3gateway.security.UserPrincipal;
import com.jonOmar.microservice3gateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider{

    /*#######################################################*/
    /*Primer parte de implementacion en PASO 10*/
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth){

        /*Se genera la lista de permisos que posee el usuario autenticado*/
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        /*Se genera el key para desencriptar el token*/
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        /*Claims son propiedades de un token*/
        /*La composicion de un JWT token se divide en 3 partes:
            1 es la información del encabezado (Encabezado)
            2 es la carga útil (Carga útil)
            3 es la firma (Firma).
        */
        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    /*#######################################################*/
    /*Segunda parte de implementacion en PASO 11*/

    /*Extrae las propiedades del token tras la validacion del mismo*/
    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if(token == null){
            return null;
        }
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims == null){
            return null;
        }

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if(username == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims == null){
            return false;
        }

        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;
    }
    /*#######################################################*/
    /* BEAN DE PASO 12*/
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }


    /*#######################################################*/
    /*Tercera parte de implementacion en PASO 16*/
    @Override
    public String generateToken(User user){
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRole())
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
