package com.modularizedmicroservice.productservice.presentation.controller;

import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ApiResponse;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.service.ProductService;
import com.modularizedmicroservice.productservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {
        try {
            ProductResponse response = productService.getProductById(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Product retrieved successfully", response));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestBody CreateProductRequest banner) {
        try {
            ProductResponse response = productService.createProduct(banner);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", response));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

}
