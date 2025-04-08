package com.modularizedmicroservice.productservice.application.usecase;

import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dtoConverter.ProductResponseDtoConverter;
import com.modularizedmicroservice.productservice.application.usecase.variation.GetVariationByProductIdUseCase;
import com.modularizedmicroservice.productservice.infrastructure.respository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductsRepository productsRepository;
    private final ProductResponseDtoConverter productResponseDtoConverter;
    private final GetVariationByProductIdUseCase getVariationByProductId;

    public Mono<ProductResponse> execute(String id) {
        return productsRepository.findByCustomId(id)
                .switchIfEmpty(Mono.error(new NullPointerException("Product not found")))
                .flatMap(product ->
                        getVariationByProductId.execute(id)
                                .map(variation -> productResponseDtoConverter.convert(product, variation))
                );
    }
}