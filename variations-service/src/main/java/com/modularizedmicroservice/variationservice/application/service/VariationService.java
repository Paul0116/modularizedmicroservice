package com.modularizedmicroservice.variationservice.application.service;

import com.modularizedmicroservice.variationservice.application.dto.request.CreateVariationRequest;
import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationService {

    private final GetVariationByIdUseCase getVariationByIdUseCase;
    private final CreateSingleVariationUseCase createSingleVariationUseCase;
    private final CreateManyVariationsUseCase createManyVariationsUseCase;
    private final GetManyVariationByIdsUseCase getManyVariationByIdsUseCase;
    private final GetManyVariationByProductIdUseCase getManyVariationByProductIdUseCase;

    public Mono<VariationResponse> getVariationById(String id) {
        return getVariationByIdUseCase.execute(id);
    }

    public Mono<VariationResponse> createVariation(CreateVariationRequest createVariationRequest) {
        return Mono.defer(() -> {
            validateProduct(createVariationRequest);
            return createSingleVariationUseCase.execute(createVariationRequest);
        });
    }

    public Flux<VariationResponse> createManyVariations(List<CreateVariationRequest> createVariationRequests) {
        return Flux.fromIterable(createVariationRequests)
                .doOnNext(this::validateProduct)
                .onErrorMap(IllegalArgumentException.class, e -> e)
                .transform(createManyVariationsUseCase::execute);
    }

    public Flux<VariationResponse> findManyByIds(List<String> ids) {
        return getManyVariationByIdsUseCase.execute(ids);
    }

    public Flux<VariationResponse> findManyByProductId(String id) {
        return getManyVariationByProductIdUseCase.execute(id);
    }

    public void validateProduct(CreateVariationRequest request) {
        validateNotEmpty(request.getImage(), "Image is required");
        validateNotEmpty(request.getSize(), "Size is required");
        validateNotEmpty(request.getTags(), "Tags are required");
        validateNotEmpty(request.getColor(), "Color is required");
        validateNotNull(request.getPrice(), "Price is required");
        validateNotNull(request.getDiscounted_price(), "Discounted price is required");
        validateNotNull(request.getBarcode(), "Barcode is required");
        validateNotNull(request.getProduct_id(), "Product id is required");
    }

    private void validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotEmpty(List<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }
}