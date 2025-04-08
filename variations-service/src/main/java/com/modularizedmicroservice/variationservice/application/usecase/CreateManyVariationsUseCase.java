package com.modularizedmicroservice.variationservice.application.usecase;

import com.modularizedmicroservice.variationservice.application.dto.request.CreateVariationRequest;
import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.dtoConverter.VariationResponseDtoConverter;
import com.modularizedmicroservice.variationservice.domain.model.Variations;

import com.modularizedmicroservice.variationservice.infrastructure.respository.VariationsRepository;
import com.modularizedmicroservice.variationservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateManyVariationsUseCase {

    private final VariationsRepository variationsRepository;
    private final VariationResponseDtoConverter variationResponseDtoConverter;

    public Flux<VariationResponse> execute(Flux<CreateVariationRequest> requests) {
        return requests
                .flatMap(request ->
                        validateIfExists(request)
                                .flatMap(existing -> Mono.<Variations>error(new ResourceAlreadyExistsException(
                                        "Variation with Barcode " + request.getBarcode() + " already exists.")))
                                .switchIfEmpty(Mono.defer(() -> Mono.just(createVariationBuilder(request))))
                                .cast(Variations.class)
                                .flatMap(variationsRepository::save)
                                .map(variationResponseDtoConverter::convert));
    }

    private Mono<Variations> validateIfExists(CreateVariationRequest request) {
        return variationsRepository.findByCustomBarcode(request.getBarcode());
    }

    private Variations createVariationBuilder(CreateVariationRequest request) {
        return Variations.builder()
                .id(request.getId())
                .image(request.getImage())
                .size(request.getSize())
                .weight(request.getWeight())
                .color(request.getColor())
                .price(request.getPrice())
                .discounted_price(request.getDiscounted_price())
                .barcode(request.getBarcode())
                .no_expiration(request.getNo_expiration())
                .start_date(request.getStart_date())
                .end_date(request.getEnd_date())
                .product_id(request.getProduct_id())
                .tags(request.getTags())
                .created_date(request.getCreated_date() != null ? request.getCreated_date() : LocalDateTime.now())
                .updated_date(request.getUpdated_date() != null ? request.getUpdated_date() : LocalDateTime.now())
                .is_delete(request.getIs_delete() != null ? request.getIs_delete() : false)
                .status(request.getStatus())
                .build();
    }
}
