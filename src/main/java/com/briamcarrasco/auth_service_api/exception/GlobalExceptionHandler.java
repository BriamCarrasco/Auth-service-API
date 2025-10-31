package com.briamcarrasco.auth_service_api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Manejador global de excepciones para la API.
 * Captura y personaliza las respuestas de error para diferentes tipos de excepciones.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de argumentos en los controladores.
     *
     * @param ex Excepción de validación.
     * @return Respuesta con detalles de los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("errores", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Maneja excepciones cuando un recurso no es encontrado.
     *
     * @param ex      Excepción de recurso no encontrado.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de recurso no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("timestamp", LocalDateTime.now());
        error.put("error", ex.getMessage());
        error.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja rutas inexistentes o no mapeadas en la API.
     *
     * @param ex      Excepción de respuesta de error.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de ruta no encontrada.
     */
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<Map<String, Object>> handleErrorResponseException(ErrorResponseException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatusCode().value());
        error.put("timestamp", LocalDateTime.now());
        error.put("error", "La URL solicitada no existe o no está disponible.");
        error.put("path", request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    /**
     * Maneja argumentos inválidos en la lógica de negocio.
     *
     * @param ex      Excepción de argumento ilegal.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de argumento inválido.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDateTime.now());
        error.put("error", ex.getMessage());
        error.put("path", request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja cualquier otra excepción no controlada.
     *
     * @param ex      Excepción general.
     * @param request Información de la petición.
     * @return Respuesta con mensaje de error interno del servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, WebRequest request) {
        // Evitar interceptar peticiones a Swagger
        String path = request.getDescription(false).replace("uri=", "");
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            throw new RuntimeException(ex); // deja que Spring maneje el error
        }

        log.error("Error interno del servidor: ", ex);

        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("timestamp", LocalDateTime.now());
        error.put("error", "Error interno del servidor");
        error.put("message", "Ha ocurrido un problema inesperado. Por favor, intente más tarde.");
        error.put("path", path);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}