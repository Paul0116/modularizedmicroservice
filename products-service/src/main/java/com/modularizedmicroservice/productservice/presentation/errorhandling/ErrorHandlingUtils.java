package com.modularizedmicroservice.productservice.presentation.errorhandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modularizedmicroservice.productservice.application.dto.response.ErrorResponse;
import com.modularizedmicroservice.productservice.presentation.exception.ProductCreationException;
import com.modularizedmicroservice.productservice.presentation.exception.ResourceAlreadyExistsException;
import com.modularizedmicroservice.productservice.presentation.exception.VariationValidationException;

import reactor.core.publisher.Mono;



public class ErrorHandlingUtils {


    public static String extractCleanErrorMessage(Throwable error, ObjectMapper objectMapper) {
        if (error instanceof ProductCreationException || error instanceof VariationValidationException) {
            return error.getMessage();
        }
        try {
            ErrorResponse errorResponse = objectMapper.readValue(error.getMessage(), ErrorResponse.class);
            return errorResponse.getMessage();
        } catch (JsonProcessingException e) {
            return error.getMessage();
        }
    }

    public static <T> Mono<T> handleErrors(Throwable ex, ObjectMapper objectMapper) {
        // Handle nested JSON errors
        String errorMessage = ex.getMessage();
        if (errorMessage != null && errorMessage.contains("{\"status\":")) {
            try {
                ErrorResponse nestedError = objectMapper.readValue(
                        errorMessage.substring(errorMessage.indexOf('{')),
                        ErrorResponse.class);
                System.out.print(nestedError.getStatus());
                if(nestedError.getStatus() == 409)
                return Mono.error(new ResourceAlreadyExistsException(
                        nestedError.getMessage()));
                if(nestedError.getStatus() == 400)
                    return Mono.error(new IllegalArgumentException(
                            nestedError.getMessage()));
                if(nestedError.getStatus() == 500)
                    return Mono.error(new RuntimeException(
                            nestedError.getMessage()));

            } catch (Exception e) {
                // Fall through to default error
            }
        }

        return Mono.error(new IllegalArgumentException(
                ex.getMessage()));
    }
}