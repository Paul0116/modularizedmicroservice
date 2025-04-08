package com.modularizedmicroservice.productservice.presentation.exception;

import org.springframework.http.HttpStatus;

public class ProductCreationException extends RuntimeException {
    private final HttpStatus status;

    public ProductCreationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}