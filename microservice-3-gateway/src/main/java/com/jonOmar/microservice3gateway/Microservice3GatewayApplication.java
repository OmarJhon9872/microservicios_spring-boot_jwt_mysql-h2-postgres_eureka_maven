package com.jonOmar.microservice3gateway;
/*PASO 19: prev:request/FeignConfiguration
* - Habilitamos el cliente Feign para poder hacer implementacion
* del proyecto gracias a las configuraciones del paso anterior
* - Se agrega la ruta 1 a configurar para el acceso a la microservice 1
* en el archivo de propiedades:
* -inmueble.service.url
* - Pasar a PASO 20 en request/InmuebleServiceRequest*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableFeignClients
@SpringBootApplication
public class Microservice3GatewayApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Microservice3GatewayApplication.class, args);
	}

}
