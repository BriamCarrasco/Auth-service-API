package com.briamcarrasco.auth_service_api.controller;

import com.briamcarrasco.auth_service_api.model.User;
import com.briamcarrasco.auth_service_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para la gestión de usuarios.
 * Permite consultar, actualizar y eliminar usuarios.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene la lista de todos los usuarios.
     * @return Lista de usuarios.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario.
     * @return Usuario encontrado o 404 si no existe.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}