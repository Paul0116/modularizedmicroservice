package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VariationsRepository extends ReactiveMongoRepository<Variations, String>, VariationCustomRepository {
}
