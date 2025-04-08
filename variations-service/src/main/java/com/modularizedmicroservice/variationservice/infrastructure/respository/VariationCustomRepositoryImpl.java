package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class VariationCustomRepositoryImpl implements VariationCustomRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public VariationCustomRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Variations> findByCustomId(String id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("is_delete").is(false)
                .and("status").is(1));
        return reactiveMongoTemplate.findOne(query, Variations.class);
    }

    @Override
    public Mono<Variations> findByCustomBarcode(String barcode) {
        Query query = new Query(Criteria.where("barcode").is(barcode)
                .and("is_delete").is(false)
                .and("status").is(1));
        return reactiveMongoTemplate.findOne(query, Variations.class);
    }

    @Override
    public Flux<Variations> findManyByIds(List<String> ids) {
        Query query = new Query(Criteria.where("_id").in(ids));
        return reactiveMongoTemplate.find(query, Variations.class);
    }

    @Override
    public Flux<Variations> findManyByProductId(String id) {
        Query query = new Query(Criteria.where("product_id").is(id));
        return reactiveMongoTemplate.find(query, Variations.class);
    }
}