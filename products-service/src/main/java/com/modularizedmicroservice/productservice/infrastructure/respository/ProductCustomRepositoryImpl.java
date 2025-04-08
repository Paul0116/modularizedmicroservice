package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ProductCustomRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Products> findByCustomId(String id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("is_delete").is(false)
                .and("status").is(1));
        return reactiveMongoTemplate.findOne(query, Products.class);
    }

    @Override
    public Mono<Products> findByCustomSku(String sku) {
        Query query = new Query(Criteria.where("sku").is(sku)
                .and("is_delete").is(false)
                .and("status").is(1));
        return reactiveMongoTemplate.findOne(query, Products.class);
    }
}