package com.modularizedmicroservice.productservice.presentation.exception;


import org.springframework.http.HttpStatus;

public class VariationValidationException extends RuntimeException {
    private final HttpStatus status;

    public VariationValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}