package com.modularizedmicroservice.productservice.presentation.controller;

import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ApiResponse;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.service.ProductService;
import com.modularizedmicroservice.productservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<ProductResponse>>> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(response -> ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(HttpStatus.OK.value(), "Product retrieved successfully", response)))
                .onErrorResume(NullPointerException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))));
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<ApiResponse<ProductResponse>>> create(@RequestBody CreateProductRequest banner) {
        return productService.createProduct(banner)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", response)))
                .onErrorResume(ResourceAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null))))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null))))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null))));
    }
}
