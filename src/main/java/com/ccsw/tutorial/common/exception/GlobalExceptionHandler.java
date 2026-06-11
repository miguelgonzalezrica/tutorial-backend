package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeleteNonExistingEntityException.class)
    public ResponseEntity<String> handleDeleteNonExistingEntity(DeleteNonExistingEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClientNameAlreadyExistsException.class)
    public ResponseEntity<String> handleClientNameAlreadyExists(ClientNameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyHasTwoLoansException.class)
    public ResponseEntity<String> handleClientAlreadyHasTwoLoans(ClientAlreadyHasTwoLoansException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(GameAlreadyHasALoanException.class)
    public ResponseEntity<String> handleGameAlreadyHasALoan(GameAlreadyHasALoanException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}