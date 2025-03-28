package com.modularizedmicroservice.productservice.application.dtoConverter;

import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dto.response.variation.VariationResponse;
import com.modularizedmicroservice.productservice.domain.model.Products;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductResponseDtoConverter {

    public ProductResponse convert(Products product, Mono<List<VariationResponse>> variation) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getTags(),
                product.getCategory_id(),
                product.getStatus(),
                product.getIs_delete(),
                product.getCreated_date(),
                product.getUpdated_date(),
                variation.block()
        );
    }
}
