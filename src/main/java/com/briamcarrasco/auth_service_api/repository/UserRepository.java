package com.briamcarrasco.auth_service_api.repository;

import com.briamcarrasco.auth_service_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad User.
 * Permite operaciones CRUD y consultas personalizadas sobre los usuarios.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verifica si existe un usuario con el email especificado.
     *
     * @param email Email a verificar.
     * @return true si existe un usuario con ese email, false en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el nombre de usuario especificado.
     *
     * @param username Nombre de usuario a verificar.
     * @return true si existe un usuario con ese nombre, false en caso contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Un Optional con el usuario si existe, o vacío si no.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Un Optional con el usuario si existe, o vacío si no.
     */
    Optional<User> findByEmail(String email);

}