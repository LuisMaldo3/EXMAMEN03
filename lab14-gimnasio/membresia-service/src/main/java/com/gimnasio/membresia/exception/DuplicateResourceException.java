package com.gimnasio.membresia.exception;

/** Recurso duplicado, por ejemplo DNI o correo repetido (HTTP 409). */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
