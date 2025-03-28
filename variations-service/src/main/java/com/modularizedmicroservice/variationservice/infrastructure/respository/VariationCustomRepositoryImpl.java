package com.modularizedmicroservice.variationservice.infrastructure.respository;

import com.modularizedmicroservice.variationservice.domain.model.Variations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VariationCustomRepositoryImpl implements VariationCustomRepository {
    private final MongoTemplate mongoTemplate;

    public VariationCustomRepositoryImpl(MongoTemplate mongoTemplate) {this.mongoTemplate = mongoTemplate;}

    @Override
    public Optional<Variations> findByCustomId(String id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("is_delete").is(false)
                .and("status").is(1));

        return Optional.ofNullable(mongoTemplate.findOne(query, Variations.class));
    }

    @Override
    public Optional<Variations> findByCustomBarcode(String barcode) {
        Query query = new Query(Criteria.where("barcode").is(barcode)
                .and("is_delete").is(false)
                .and("status").is(1));
        return Optional.ofNullable(mongoTemplate.findOne(query, Variations.class));
    }

    @Override
    public List<Variations> findManyByIds(List<String> ids) {
        Query query = new Query(Criteria.where("_id").in(ids));
        return mongoTemplate.find(query, Variations.class);
    }

    @Override
    public List<Variations> findManyByProductId(String id) {
        Query query = new Query(Criteria.where("product_id").is(id));
        return mongoTemplate.find(query, Variations.class);
    }




}
