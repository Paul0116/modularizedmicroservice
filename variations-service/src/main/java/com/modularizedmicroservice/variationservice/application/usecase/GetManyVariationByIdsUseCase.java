package com.modularizedmicroservice.variationservice.application.usecase;

import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.dtoConverter.VariationResponseDtoConverter;

import com.modularizedmicroservice.variationservice.infrastructure.respository.VariationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetManyVariationByIdsUseCase {

    private final VariationsRepository variationsRepository;
    private final VariationResponseDtoConverter variationResponseDtoConverter;

    public Flux<VariationResponse> execute(List<String> ids) {
        return variationsRepository.findManyByIds(ids)
                .switchIfEmpty(Mono.error(new NullPointerException("Variation not found")))
                .map(variationResponseDtoConverter::convert);
    }
}
