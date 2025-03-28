package com.modularizedmicroservice.variationservice.application.dtoConverter;

import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.domain.model.Variations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VariationResponseDtoConverter {

    public VariationResponse convert(Variations variations) {
        return new VariationResponse(
                variations.getId(),
                variations.getImage(),
                variations.getSize(),
                variations.getWeight(),
                variations.getColor(),
                variations.getPrice(),
                variations.getDiscounted_price(),
                variations.getBarcode(),
                variations.getNo_expiration(),
                variations.getStart_date(),
                variations.getEnd_date(),
                variations.getProduct_id(),
                variations.getTags(),
                variations.getCreated_date(),
                variations.getUpdated_date(),
                variations.getIs_delete(),
                variations.getStatus()
        );
    }
    public List<VariationResponse> convertList(List<Variations> variationsList) {
        return variationsList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
