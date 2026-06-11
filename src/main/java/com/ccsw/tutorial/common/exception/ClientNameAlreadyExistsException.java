package com.ccsw.tutorial.common.exception;

public class ClientNameAlreadyExistsException extends RuntimeException {
    public ClientNameAlreadyExistsException(String message) {
        super(message);
    }
}