package com.example.ebanxapi.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<?> handleAccountNotFound() {
        return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
    }
}
