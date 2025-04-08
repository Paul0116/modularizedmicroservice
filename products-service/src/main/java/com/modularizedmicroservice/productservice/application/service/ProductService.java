package com.modularizedmicroservice.productservice.application.service;

import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.usecase.CreateSingleProductUseCase;
import com.modularizedmicroservice.productservice.application.usecase.GetProductByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final GetProductByIdUseCase getProductByIdUseCase;
    private final CreateSingleProductUseCase createSingleProductUseCase;

    public Mono<ProductResponse> getProductById(String id) {
        return getProductByIdUseCase.execute(id);
    }

    public Mono<ProductResponse> createProduct(CreateProductRequest createProductRequest) {
        return Mono.fromRunnable(() -> validateProduct(createProductRequest)) // Ensure validation runs first
                .then(Mono.defer(() -> createSingleProductUseCase.execute(createProductRequest))); // Execute reactively
    }


    private void validateProduct(CreateProductRequest request) {
        validateNotEmpty(request.getName(), "Product name cannot be empty");
        validateNotNull(request.getCategory_id(), "Category id is required");
        validateNotEmpty(request.getTags(), "Tags are required");
        validateNotEmpty(request.getSku(), "SKU is required");
        validateNotEmpty(request.getDescription(), "Description is required");
    }

    private void validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }


    private void validateNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }
    }