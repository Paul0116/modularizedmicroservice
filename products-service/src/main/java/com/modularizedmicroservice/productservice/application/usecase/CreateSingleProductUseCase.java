package com.modularizedmicroservice.productservice.application.usecase;

import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dtoConverter.ProductResponseDtoConverter;
import com.modularizedmicroservice.productservice.domain.model.Products;
import com.modularizedmicroservice.productservice.infrastructure.respository.ProductsRepository;
import com.modularizedmicroservice.productservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateSingleProductUseCase {

    private final ProductsRepository productsRepository;
    private final ProductResponseDtoConverter productResponseDtoConverter;
    private final GetVariationByProductIdUseCase getVariationByProductId;

    public ProductResponse execute(CreateProductRequest request) {

        if (validateIfExists(request).isPresent()) {
            throw new ResourceAlreadyExistsException("Product with SKU " + request.getSku() + " already exists.");
        }

        Products products = Products.builder()
                .name(request.getName())
                .description(request.getDescription())
                .tags(request.getTags())
                .sku(request.getSku())
                .category_id(request.getCategory_id())
                .status(request.getStatus())
                .status(1)
                .is_delete(false)
                .created_date(LocalDateTime.now())
                .updated_date(LocalDateTime.now())
                .build();

        products = productsRepository.save(products);

        return productResponseDtoConverter.convert(products, getVariationByProductId.execute(products.getId()));
    }

    public Optional<Products> validateIfExists(CreateProductRequest request) {
        return productsRepository.findByCustomSku(request.getSku());
    }


}
