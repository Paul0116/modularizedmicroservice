package com.modularizedmicroservice.variationservice.application.usecase;

import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.dtoConverter.VariationResponseDtoConverter;
import com.modularizedmicroservice.variationservice.domain.model.Variations;
import com.modularizedmicroservice.variationservice.infrastructure.respository.VariationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeyManyVariationByProductIdUseCase {

    private final VariationsRepository variationsRepository;
    private final VariationResponseDtoConverter variationResponseDtoConverter;

    public List<VariationResponse> execute(String ids) {
        List<Variations> variations = variationsRepository.findManyByProductId(ids);
        if (variations.isEmpty()) {
            throw new NullPointerException("variation not found");
        }
        return variationResponseDtoConverter.convertList(variations);
    }
}
