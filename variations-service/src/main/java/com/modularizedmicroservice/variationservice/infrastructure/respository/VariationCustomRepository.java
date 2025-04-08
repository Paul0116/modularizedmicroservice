package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface VariationCustomRepository {
    Mono<Variations> findByCustomId(String id);
    Mono<Variations> findByCustomBarcode(String barcode);
    Flux<Variations> findManyByIds(List<String> ids);
    Flux<Variations> findManyByProductId(String id);
}
