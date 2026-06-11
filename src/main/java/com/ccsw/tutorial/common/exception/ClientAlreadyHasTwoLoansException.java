package com.ccsw.tutorial.common.exception;

public class ClientAlreadyHasTwoLoansException extends RuntimeException {
    public ClientAlreadyHasTwoLoansException(String message) {
        super(message);
    }
}
