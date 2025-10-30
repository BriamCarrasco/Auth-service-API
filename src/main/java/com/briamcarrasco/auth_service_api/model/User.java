package com.briamcarrasco.auth_service_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un usuario del sistema.
 * Incluye información personal, credenciales y rol de acceso.
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long id;

    /**
     * Nombre del usuario.
     */
    @Column(name = "name")
    private String name;

    /**
     * Primer apellido del usuario.
     */
    @Column(name = "first_lastname")
    private String firstLastname;

    /**
     * Segundo apellido del usuario.
     */
    @Column(name = "second_lastname")
    private String secondLastname;

    /**
     * Correo electrónico del usuario. Debe ser único.
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Nombre de usuario. Debe ser único.
     */
    @Column(name = "username", unique = true)
    private String username;

    /**
     * Contraseña del usuario.
     * Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.
     */
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
    )
    @Column(name = "password")
    private String password;

    /**
     * Rol del usuario en el sistema (por ejemplo: "users" o "admin").
     */
    @Column(name = "role")
    private String role;

    /**
     * RUT chileno del usuario.
     * Debe tener el formato 12345678-5 y entre 9 y 12 caracteres.
     */
    @Pattern(
        regexp = "^\\d{7,8}-[\\dkK]$",
        message = "El RUT debe tener el formato 12345678-5"
    )
    @NotNull
    @Size(max = 12, min=9, message = "El RUT debe tener entre 9 y 12 caracteres")
    @Column(name = "rut")
    private String rut;

    /**
     * Constructor vacío.
     */
    public User() {
    }

    // Getters y setters generados automáticamente por Lombok (@Data)
   
}