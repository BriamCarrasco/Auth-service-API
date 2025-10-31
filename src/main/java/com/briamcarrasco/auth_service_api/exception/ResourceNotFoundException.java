package com.briamcarrasco.auth_service_api.exception;

/**
 * Excepción personalizada para indicar que un recurso no fue encontrado.
 * Se utiliza principalmente en los servicios cuando no se encuentra una entidad solicitada.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Crea una nueva instancia de ResourceNotFoundException con un mensaje personalizado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
}
