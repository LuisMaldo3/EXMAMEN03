package com.gimnasio.membresia.exception;

/** Otro microservicio no responde (HTTP 503). */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
