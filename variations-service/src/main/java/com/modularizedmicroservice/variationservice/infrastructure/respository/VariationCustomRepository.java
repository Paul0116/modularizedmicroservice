package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;

import java.util.List;
import java.util.Optional;

public interface VariationCustomRepository {
    Optional<Variations> findByCustomId(String id);
    Optional<Variations> findByCustomBarcode(String barcode);
    List<Variations> findManyByIds(List<String> ids);
    List<Variations> findManyByProductId(String id);
}

