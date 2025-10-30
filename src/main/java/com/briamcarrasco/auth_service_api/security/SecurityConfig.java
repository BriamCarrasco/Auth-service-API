package com.briamcarrasco.auth_service_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Define los beans necesarios para la seguridad, como el codificador de contraseñas.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean para el codificador de contraseñas utilizando BCrypt.
     * Este bean se utiliza para encriptar y verificar contraseñas de usuarios.
     *
     * @return Implementación de PasswordEncoder basada en BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            );
        return http.build();
    }

}