package com.modularizedmicroservice.variationservice.application.usecase;

import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.dtoConverter.VariationResponseDtoConverter;
import com.modularizedmicroservice.variationservice.domain.model.Variations;
import com.modularizedmicroservice.variationservice.infrastructure.respository.VariationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetVariationByIdUseCase {

    private final VariationsRepository variationsRepository;
    private final VariationResponseDtoConverter variationResponseDtoConverter;

    public VariationResponse execute(String id) {
        Optional<Variations> variations = variationsRepository.findByCustomId(id);
        if (!variations.isPresent()) {
            throw new NullPointerException("variation not found");
        }
        return variationResponseDtoConverter.convert(variations.get());
    }

}
