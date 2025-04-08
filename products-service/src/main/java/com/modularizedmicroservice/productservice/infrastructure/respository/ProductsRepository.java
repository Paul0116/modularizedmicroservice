package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductsRepository extends ReactiveMongoRepository<Products, String>, ProductCustomRepository {
}