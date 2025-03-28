package com.modularizedmicroservice.productservice.application.service;

import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dto.response.variation.VariationResponse;
import com.modularizedmicroservice.productservice.application.usecase.CreateSingleProductUseCase;
import com.modularizedmicroservice.productservice.application.usecase.GetProductByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final GetProductByIdUseCase getProductByIdUseCase;
    private final CreateSingleProductUseCase createSingleProductUseCase;



    public ProductResponse getProductById(String id) {
        return getProductByIdUseCase.execute(id);
    }



    public ProductResponse createProduct(CreateProductRequest createProductRequest) {

        validateProduct(createProductRequest);

        return createSingleProductUseCase.execute(createProductRequest);

    }

    public void validateProduct(CreateProductRequest request) {
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

    private void validateNotEmpty(List<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }


}
