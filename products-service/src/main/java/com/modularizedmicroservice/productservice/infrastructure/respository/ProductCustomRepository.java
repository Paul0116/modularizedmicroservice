package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;



import reactor.core.publisher.Mono;

public interface ProductCustomRepository {
    Mono<Products> findByCustomId(String id);
    Mono<Products> findByCustomSku(String sku);
}