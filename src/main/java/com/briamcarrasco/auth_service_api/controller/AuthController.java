package com.briamcarrasco.auth_service_api.controller;

import com.briamcarrasco.auth_service_api.model.User;
import com.briamcarrasco.auth_service_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // asegura que esté este import
import java.util.Optional;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro y login de usuarios")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Registra un usuario en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos del usuario a registrar",
            content = @Content(schema = @Schema(implementation = User.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@org.springframework.web.bind.annotation.RequestBody @Valid User user) { // usar RequestBody de Spring aquí
        System.out.println("Usuario recibido: " + user);
        System.out.println("RUT recibido: " + user.getRut());
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
    }

    @Operation(
        summary = "Login de usuario",
        description = "Autentica a un usuario usando username y password.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( // FQN de Swagger
            required = true,
            description = "Credenciales de acceso",
            content = @Content(schema = @Schema(implementation = User.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@org.springframework.web.bind.annotation.RequestBody User user) { // RequestBody de Spring
        Optional<User> userOpt = userService.login(user.getUsername(), user.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}