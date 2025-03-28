package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepository extends MongoRepository<Products, String>, ProductCustomRepository {
}
