package com.jon.microservice1inmuebles.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*EnableWebSecurity indica que cuando un request sea recibido tendra
que pasar por esta clase para su validacion*/
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /*Usuario 1*/
    @Value("${service.sercurity.secure-key-username}")
    private String SECURE_KEY_USERNAME;
    @Value("${service.sercurity.secure-key-password}")
    private String SECURE_KEY_PASSWORD;

    /*Usuario 2*/
    @Value("${service.sercurity.secure-key-username-2}")
    private String SECURE_KEY_USERNAME_2;
    @Value("${service.sercurity.secure-key-password-2}")
    private String SECURE_KEY_PASSWORD_2;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        /*Primera parte de la comprobacion de peticiones, comprobacion a nivel de permisos de usuarios*/
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(SECURE_KEY_USERNAME)
                .password(new BCryptPasswordEncoder().encode(SECURE_KEY_PASSWORD))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"))
                .and()
                .withUser(SECURE_KEY_USERNAME_2)
                .password(new BCryptPasswordEncoder().encode(SECURE_KEY_PASSWORD_2))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEV"))
                .and()
                .passwordEncoder(new BCryptPasswordEncoder());

        /*Segunda parte de comprobacion de seguridad en peticiones a nivel de los objetos a proteger*/
        return http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .csrf()
                .disable()
                .httpBasic()
                /*Los siguientes metodos se emplean cuando el manejo de usuarios es a base
                * de sesiones en una aplicacion web tradicional con proteccion CSRF activa
                * pero como en este caso se deshabilito se comentaran estas lineas*/
                /*.and()
                .exceptionHandling()
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.sendRedirect("localhost");
                }))*/
                .and()
                .build();
    }
}
