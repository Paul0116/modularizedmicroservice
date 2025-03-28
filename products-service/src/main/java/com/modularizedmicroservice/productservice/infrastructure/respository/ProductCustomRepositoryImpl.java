package com.modularizedmicroservice.productservice.infrastructure.respository;

import com.modularizedmicroservice.productservice.domain.model.Products;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final MongoTemplate mongoTemplate;

    public ProductCustomRepositoryImpl(MongoTemplate mongoTemplate) {this.mongoTemplate = mongoTemplate;}

    @Override
    public Optional<Products> findByCustomId(String id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("is_delete").is(false)
                .and("status").is(1));

        return Optional.ofNullable(mongoTemplate.findOne(query, Products.class));
    }

    @Override
    public Optional<Products> findByCustomSku(String sku) {
        Query query = new Query(Criteria.where("sku").is(sku)
                .and("is_delete").is(false)
                .and("status").is(1));
        return Optional.ofNullable(mongoTemplate.findOne(query, Products.class));
    }


}
