package com.briamcarrasco.auth_service_api.service;

import com.briamcarrasco.auth_service_api.model.User;
import java.util.Optional;
import java.util.List;

/**
 * Interfaz para la lógica de negocio relacionada con usuarios.
 */
public interface UserService {
    
    /**
     * Registra un nuevo usuario en el sistema.
     * @param user Usuario a registrar.
     * @return Usuario registrado.
     */
    User register(User user);

    /**
     * Autentica a un usuario con username y password.
     * @param username Nombre de usuario.
     * @param password Contraseña en texto plano.
     * @return Usuario autenticado si las credenciales son correctas, vacío si no.
     */
    Optional<User> login(String username, String password);

    /**
     * Actualiza la información de un usuario existente.
     * @param user Usuario con los datos actualizados.
     * @return Usuario actualizado.
     */
    User update(User user);

    /**
     * Elimina un usuario por su ID.
     * @param id Identificador del usuario a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario.
     * @return Usuario encontrado, vacío si no existe.
     */
    User findById(Long id);

    /**
     * Obtiene la lista de todos los usuarios.
     * @return Lista de usuarios.
     */
    List<User> findAll();
}