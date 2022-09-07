package com.jonOmar.microservice3gateway.request;
/*PASO 18: prev:service/UserServiceImpl
*
* - Para poderse comunicar con los microservicios inmuebles y compras
* deberemos usar una autenticacion de tipo Basic empleando el metodo
* basicAuthRequestInterceptor que recibe usuario y contraseña de ambos
* microservicios
* - Para habilitar el servicio de Feign en el proyecto se debera implementar
* desde la clase principal de este.
* - Pasar a PASO 19 en Microservice3GatewayApplication*/

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${service.sercurity.secure-key-username}") String username,
            @Value("${service.sercurity.secure-key-password}") String password
    ){
        return new BasicAuthRequestInterceptor(username, password);
    }
}
