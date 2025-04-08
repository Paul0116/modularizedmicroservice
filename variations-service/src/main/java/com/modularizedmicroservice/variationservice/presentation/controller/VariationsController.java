package com.modularizedmicroservice.variationservice.presentation.controller;

import com.modularizedmicroservice.variationservice.application.dto.request.CreateVariationRequest;
import com.modularizedmicroservice.variationservice.application.dto.response.ApiResponse;
import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.service.VariationService;
import com.modularizedmicroservice.variationservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/variations")
@RequiredArgsConstructor
public class VariationsController {

    private final VariationService variationService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<VariationResponse>>> getProductById(@PathVariable String id) {
        return variationService.getVariationById(id)
                .map(response -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Variation retrieved successfully",
                                response)))
                .onErrorResume(NullPointerException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Variation not found", null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve variation: " + e.getMessage(), null))));
    }

    @PostMapping("/create-many")
    public Mono<ResponseEntity<ApiResponse<List<VariationResponse>>>> createMany(
            @RequestBody List<CreateVariationRequest> createVariationRequests) {
        return variationService.createManyVariations(createVariationRequests)
                .collectList()
                .map(variationResponses -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Variations created successfully",
                                variationResponses)))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null))))
                .onErrorResume(ResourceAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null))))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create variations: " + e.getMessage(), null))));
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<ApiResponse<VariationResponse>>> create(@RequestBody CreateVariationRequest banner) {
        return variationService.createVariation(banner)
                .map(response -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Variation created successfully",
                                response)))
                .onErrorResume(ResourceAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null))))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create variation: " + e.getMessage(), null))));
    }

    @PostMapping("/find-by-ids")
    public Mono<ResponseEntity<ApiResponse<List<VariationResponse>>>> findByIds(@RequestBody List<String> ids) {
        return variationService.findManyByIds(ids)
                .collectList()
                .map(response -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Variations retrieved successfully",
                                response)))
                .onErrorResume(ResourceAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null))))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve variations: " + e.getMessage(), null))));
    }

    @GetMapping("/find-by-product-id/{id}")
    public Mono<ResponseEntity<ApiResponse<List<VariationResponse>>>> findByProductIds(@PathVariable String id) {
        return variationService.findManyByProductId(id)
                .collectList()
                .map(response -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Variations retrieved successfully",
                                response)))
                .onErrorResume(ResourceAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null))))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve variations: " + e.getMessage(), null))));
    }
}