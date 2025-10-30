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


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_lastname")
    private String firstLastname;

    @Column(name = "second_lastname")
    private String secondLastname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    @Pattern(
        regexp = "^\\d{7,8}-[\\dkK]$",
        message = "El RUT debe tener el formato 12345678-5"
    )
    @NotNull
    @Size(max = 12, min=9, message = "El RUT debe tener entre 9 y 12 caracteres")
    @Column(name = "rut")
    private String rut;




    
}
