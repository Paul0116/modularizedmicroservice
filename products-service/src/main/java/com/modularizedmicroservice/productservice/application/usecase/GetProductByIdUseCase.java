package com.modularizedmicroservice.productservice.application.usecase;

import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dto.response.variation.VariationResponse;
import com.modularizedmicroservice.productservice.application.dtoConverter.ProductResponseDtoConverter;
import com.modularizedmicroservice.productservice.domain.model.Products;
import com.modularizedmicroservice.productservice.infrastructure.respository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductsRepository productsRepository;
    private final ProductResponseDtoConverter productResponseDtoConverter;
    private final GetVariationByProductIdUseCase getVariationByProductId;

    public ProductResponse execute(String id) {
        Optional<Products> products = productsRepository.findByCustomId(id);
        if (!products.isPresent()) {
            throw new NullPointerException("product not found");
        }

        return productResponseDtoConverter.convert(products.get(), getVariationByProductId.execute(id));
    }





}
