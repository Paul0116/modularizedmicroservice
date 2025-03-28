package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariationsRepository extends MongoRepository<Variations, String>, VariationCustomRepository {
}
