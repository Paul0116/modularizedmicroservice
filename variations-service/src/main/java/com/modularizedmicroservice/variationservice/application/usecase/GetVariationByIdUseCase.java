package com.modularizedmicroservice.variationservice.application.usecase;

import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.dtoConverter.VariationResponseDtoConverter;

import com.modularizedmicroservice.variationservice.infrastructure.respository.VariationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetVariationByIdUseCase {

    private final VariationsRepository variationsRepository;
    private final VariationResponseDtoConverter variationResponseDtoConverter;

    public Mono<VariationResponse> execute(String id) {
        return variationsRepository.findByCustomId(id)
                .switchIfEmpty(Mono.error(new NullPointerException("Variation not found")))
                .map(variationResponseDtoConverter::convert);
    }
}