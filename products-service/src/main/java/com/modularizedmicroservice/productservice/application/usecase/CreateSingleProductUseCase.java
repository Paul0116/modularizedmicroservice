package com.modularizedmicroservice.productservice.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.request.variation.CreateVariationRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dtoConverter.ProductResponseDtoConverter;
import com.modularizedmicroservice.productservice.application.usecase.variation.CreateVariationsByProductIdUseCase;
import com.modularizedmicroservice.productservice.domain.model.Products;
import com.modularizedmicroservice.productservice.infrastructure.respository.ProductsRepository;
import com.modularizedmicroservice.productservice.presentation.errorhandling.ErrorHandlingUtils;
import com.modularizedmicroservice.productservice.presentation.exception.ResourceAlreadyExistsException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSingleProductUseCase {

    private final ProductsRepository productsRepository;
    private final ProductResponseDtoConverter productResponseDtoConverter;
    private final CreateVariationsByProductIdUseCase createVariationsByProductId;
    private final ObjectMapper objectMapper;

    @Transactional
    public Mono<ProductResponse> execute(CreateProductRequest request) {
        return validateIfExists(request)
                .flatMap(existingProduct ->
                        Mono.error(new ResourceAlreadyExistsException(
                                "Product with SKU " + request.getSku() + " already exists."))
                )
                .switchIfEmpty(Mono.defer(() ->
                        createProductAndVariations(request)
                                .onErrorResume(error -> productsRepository.findByCustomSku(request.getSku())
                                        .flatMap(product -> productsRepository.deleteById(product.getId()))
                                        .then(Mono.error(error))
                                )
                ))
                .cast(ProductResponse.class)
                .onErrorResume(error -> ErrorHandlingUtils.handleErrors(error, objectMapper));
    }


    private Mono<ProductResponse> createProductAndVariations(CreateProductRequest request) {
        Products product = buildProductFromRequest(request);

        return productsRepository.save(product)
                .flatMap(savedProduct -> {
                    List<CreateVariationRequest> variations = prepareVariations(request, savedProduct.getId());
                    return createVariationsByProductId.execute(variations)
                            .flatMap(variationList ->
                                    Mono.just(productResponseDtoConverter.convert(savedProduct, variationList))
                                            .onErrorResume(variationError -> {
                                                String cleanMessage = ErrorHandlingUtils.extractCleanErrorMessage(variationError, objectMapper);
                                                return Mono.error(new IllegalArgumentException(
                                                       cleanMessage
                                                ));
                                            }));
                });
    }


    private Products buildProductFromRequest(CreateProductRequest request) {
        return Products.builder()
                .name(request.getName())
                .description(request.getDescription())
                .tags(request.getTags())
                .sku(request.getSku())
                .category_id(request.getCategory_id())
                .status(1)
                .is_delete(false)
                .created_date(LocalDateTime.now())
                .updated_date(LocalDateTime.now())
                .build();
    }

    private List<CreateVariationRequest> prepareVariations(CreateProductRequest request, String productId) {
        List<CreateVariationRequest> variations = request.getVariations();
        variations.forEach(v -> v.setProduct_id(productId));
        return variations;
    }

    public Mono<Products> validateIfExists(CreateProductRequest request) {
        return productsRepository.findByCustomSku(request.getSku());
    }
}
