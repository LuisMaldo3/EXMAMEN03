package com.gimnasio.plan.exception;

/** Regla de negocio incumplida (HTTP 400). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
