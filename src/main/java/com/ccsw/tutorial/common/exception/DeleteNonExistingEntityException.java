package com.ccsw.tutorial.common.exception;

public class DeleteNonExistingEntityException extends RuntimeException {
    public DeleteNonExistingEntityException(String message) {
        super(message);
    }
}