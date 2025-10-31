package com.briamcarrasco.auth_service_api.controller;

import com.briamcarrasco.auth_service_api.model.User;
import com.briamcarrasco.auth_service_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controlador para autenticación y registro de usuarios.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro y login de usuarios")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param user Datos del usuario a registrar.
     * @return Usuario registrado o error si ya existe.
     */
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Registra un usuario en el sistema.",
        requestBody = @RequestBody(
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
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Endpoint para login de usuario.
     * @param user Datos de login (username y password).
     * @return Usuario autenticado o error si las credenciales no son válidas.
     */
    @Operation(
        summary = "Login de usuario",
        description = "Autentica a un usuario usando username y password.",
        requestBody = @RequestBody(
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
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> userOpt = userService.login(user.getUsername(), user.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}