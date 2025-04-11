package com.modularizedmicroservice.productservice.application.usecase.variation;

import com.modularizedmicroservice.productservice.application.dto.request.variation.CreateVariationRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ApiResponse;
import com.modularizedmicroservice.productservice.application.dto.response.variation.VariationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateVariationsByProductIdUseCase {

    private final WebClient.Builder webClientBuilder;

    public Mono<List<VariationResponse>> execute(List<CreateVariationRequest> variationRequests) {
        return webClientBuilder.build()
                .post()
                .uri("http://variation-service/variations/create-many")
                .bodyValue(variationRequests)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                        "Error creating variations: " + errorBody)))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<VariationResponse>>>() {})
                .map(ApiResponse::getData)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error creating variations: " +e.getMessage(), e)));
    }
}r