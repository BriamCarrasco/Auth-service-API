package com.briamcarrasco.auth_service_api.controller;

import com.briamcarrasco.auth_service_api.model.User;
import com.briamcarrasco.auth_service_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controlador para la gestión de usuarios.
 * Permite consultar, actualizar y eliminar usuarios.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Operaciones para la gestión de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene la lista de todos los usuarios.
     * @return Lista de usuarios.
     */
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve una lista con todos los usuarios registrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                content = @Content(schema = @Schema(implementation = User.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario.
     * @return Usuario encontrado o 404 si no existe.
     */
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Devuelve un usuario según su identificador.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Actualiza la información de un usuario.
     * @param user Usuario con los datos actualizados.
     * @return Usuario actualizado o error si no existe.
     */
    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente.",
        requestBody = @RequestBody(
            required = true,
            description = "Datos actualizados del usuario",
            content = @Content(schema = @Schema(implementation = User.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        }
    )
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param id Identificador del usuario a eliminar.
     * @return Respuesta vacía si se elimina correctamente.
     */
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario según su identificador.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}