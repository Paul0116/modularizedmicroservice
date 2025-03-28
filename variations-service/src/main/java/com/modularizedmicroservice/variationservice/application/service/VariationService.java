package com.modularizedmicroservice.variationservice.application.service;

import com.modularizedmicroservice.variationservice.application.dto.request.CreateVariationRequest;
import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.usecase.CreateSingleVariationUseCase;
import com.modularizedmicroservice.variationservice.application.usecase.GetManyVariationByIdsUseCase;
import com.modularizedmicroservice.variationservice.application.usecase.GetVariationByIdUseCase;
import com.modularizedmicroservice.variationservice.application.usecase.GeyManyVariationByProductIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationService {

    private final GetVariationByIdUseCase getVariationByIdUseCase;
    private final CreateSingleVariationUseCase createSingleVariationUseCase;
    private final GetManyVariationByIdsUseCase getManyVariationByIdsUseCase;
    private final GeyManyVariationByProductIdUseCase geyManyVariationByProductIdUseCase;

    public VariationResponse getVariationById(String id) {
        return getVariationByIdUseCase.execute(id);
    }



    public VariationResponse createVariation(CreateVariationRequest createVariationRequest) {

        validateProduct(createVariationRequest);

        return createSingleVariationUseCase.execute(createVariationRequest);

    }

    public List<VariationResponse> findManyByIds(List<String> ids) {
        return getManyVariationByIdsUseCase.execute(ids);
    }

    public List<VariationResponse> findManyByProductId(String id) {
        return geyManyVariationByProductIdUseCase.execute(id);
    }



    public void validateProduct(CreateVariationRequest request) {
        validateNotEmpty(request.getImage(), "Image is required");
        validateNotNull(request.getSize(), "Size id is required");
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
