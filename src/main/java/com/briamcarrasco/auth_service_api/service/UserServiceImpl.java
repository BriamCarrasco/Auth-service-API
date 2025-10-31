package com.briamcarrasco.auth_service_api.service;

import com.briamcarrasco.auth_service_api.exception.ResourceNotFoundException;
import com.briamcarrasco.auth_service_api.model.User;
import com.briamcarrasco.auth_service_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la lógica de negocio para usuarios.
 * Proporciona métodos para registrar, autenticar, actualizar, eliminar y consultar usuarios.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que el email y el nombre de usuario no estén registrados previamente.
     * Asigna el rol "users" si no se especifica y encripta la contraseña antes de guardar.
     *
     * @param user Usuario a registrar.
     * @return Usuario registrado.
     * @throws IllegalArgumentException si el email o el nombre de usuario ya existen.
     */
    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("users");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Autentica a un usuario verificando el nombre de usuario y la contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña en texto plano.
     * @return Usuario autenticado si las credenciales son correctas, vacío si no.
     */
    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza la información de un usuario existente.
     * Si se proporciona una nueva contraseña, la encripta antes de guardar.
     * Si no se proporciona contraseña, conserva la anterior.
     *
     * @param user Usuario con los datos actualizados.
     * @return Usuario actualizado.
     * @throws IllegalArgumentException si el usuario no existe.
     */
    @Override
    public User update(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.get().getPassword());
        }
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id Identificador del usuario a eliminar.
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su ID.
     * Lanza una excepción ResourceNotFoundException si el usuario no existe.
     *
     * @param id Identificador del usuario.
     * @return Usuario encontrado.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}