package com.ccsw.tutorial.common.exception;

public class GameAlreadyHasALoanException  extends RuntimeException {
    public GameAlreadyHasALoanException(String message) {
        super(message);
    }
}
