package com.briamcarrasco.auth_service_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 * Define los beans necesarios para la seguridad, como el codificador de contraseñas
 * y la cadena de filtros de seguridad.
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

    /**
     * Configura la cadena de filtros de seguridad de Spring Security.
     * En este caso, deshabilita CSRF y permite todas las solicitudes sin autenticación.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception Si ocurre un error en la configuración.
     */
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