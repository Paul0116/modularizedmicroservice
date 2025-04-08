package com.modularizedmicroservice.productservice.application.usecase.variation;

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
public class GetVariationByProductIdUseCase {


    private final WebClient.Builder webClientBuilder;

    public Mono<List<VariationResponse>> execute(String id) {
        return webClientBuilder.build()
                .get()
                .uri("http://variation-service/variations/find-by-product-id/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<VariationResponse>>>() {})
                .map(ApiResponse::getData); // Extract data without blocking
    }


}
