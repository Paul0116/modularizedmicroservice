package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;

import java.util.Optional;

public interface ProductCustomRepository {
    Optional<Products> findByCustomId(String id);
    Optional<Products> findByCustomSku(String sku);
}

