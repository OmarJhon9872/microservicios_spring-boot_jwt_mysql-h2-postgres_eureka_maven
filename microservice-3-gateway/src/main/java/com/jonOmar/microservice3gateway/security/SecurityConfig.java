package com.jonOmar.microservice3gateway.security;
/*PASO 8: prev:security/CustomUserDetailsService
*
* - CustomUserDetailsService contiene la personalizacion de la interfaz UserDetails
* misma que tras adecuar e implementar en security/CustomUserDetailsService, actuara
* en la parte de securityFilterChain como interceptor de autenticacion y configuracion
* para el AuthenticationManager permitiendo la manipulacion de datos en aspectos de
* seguridad para SpringSecurity bajo instancias de HttpSecurity recibida por el metodo
* securityFilterChain
* - Personalizacion de rutas que permitiran accesos api, sign-in y sign-up
* - Definicion del gestor de autenticacion "authenticationManager" con las configuraciones
* que se han estado llevando a cabo en pasos anteriores
* - Pasar a PASO 9 en application.properties */

/*PASO 22: prev:controller/InmuebleController
*
* - Se redefine la autorizacion en ############# PASO 22 ###############
* - Adecuanto el acceso libre al listado de inmuebles pero a la proteccion mediante roles
* de la creacion y eliminacion de un inmueble
* - Definimos la url ruta del microservicio de compras en el archivo application.properties
* - compras.service.url
* - Pasar a PASO 23 en request/CompraServiceRequest*/

import com.jonOmar.microservice3gateway.model.Role;
import com.jonOmar.microservice3gateway.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*Encargado de la autenticacion de los usuarios*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception{
        return authConf.getAuthenticationManager();
    }

    /*Creado en PASO 12 security/jwt/JwtAuthorizationFiler*/
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFiler(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        /*Constructor de autenticacion que configurara al userDetailsService con la instancia de
        * customUserDetailsService que personaliza a UserDetails y que se pasara como parametro
        * de autenticacion en la configuracion de este metodo con el retorno de un objeto
        * de HttpSecurity */
        AuthenticationManagerBuilder authManBuilder = httpSecurity.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        /*Crear referencia entre el AuthenticationManagerBuilder y la instancia del usuario a emplear
        * en la aplicacion*/
        authManBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);

        /*Creamos una referencia de AuthenticationManagerBuilder, mismo que al estar configurado
        * con la instancia de customUserDetailsService posee los atributos e instancias de UserDetails*/
        AuthenticationManager authenticationManager = authManBuilder.build();


        /*############# PASO 22 ############### redefinicion de objeto httpSecurity*/
        httpSecurity.cors();
        httpSecurity.csrf().disable();
        httpSecurity.authenticationManager(authenticationManager);
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*############# FIN PASO 22 ############### redefinicion de objeto httpSecurity*/

        /*Definicion de proteccion de rutas paths de la aplicacion por parte de HttpSecurity de SpringSecurity*/
        httpSecurity.authorizeHttpRequests()
            .antMatchers("/api/authentication/sign-in", "/api/authentication/sign-up")
            .permitAll()



                /*############# PASO 22 ############### redefinicion de objeto httpSecurity*/
                .antMatchers(HttpMethod.GET, "/gateway/inmueble").permitAll()
                .antMatchers("/gateway/inmueble/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class);
                /*############# PASO 22 ############### redefinicion de objeto httpSecurity
                * SE BORRA LO SIGUIENTE DEBIDO A QUE LAS CONFIGURACIONES JWT SE ESTABLECIERON AL IGUAL
                * QUE LOS DEMAS PARAMETROS DE ADMINISTRACION DE SESION Y POLITICAS*/
                /*.and()
                .authenticationManager(authenticationManager)
                    *//*Creado en PASO 12 security/jwt/JwtAuthorizationFiler*//*
                .addFilterBefore(jwtAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()

                    *//*ALWAYS Crea siempre unHttpSession*//*
                    *//*STATELESS Spring Security nunca creará HttpSession y nunca lo usará para obtener el SecurityContext*//*
                    *//*NEVER Spring Security nunca creará un HttpSession, pero usará el HttpSessionsi ya existe*//*
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/



        return httpSecurity.build();
    }
}
