package com.modularizedmicroservice.variationservice.presentation.controller;

import com.modularizedmicroservice.variationservice.application.dto.request.CreateVariationRequest;
import com.modularizedmicroservice.variationservice.application.dto.response.ApiResponse;
import com.modularizedmicroservice.variationservice.application.dto.response.VariationResponse;
import com.modularizedmicroservice.variationservice.application.service.VariationService;
import com.modularizedmicroservice.variationservice.presentation.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/variations")
@RequiredArgsConstructor
public class VariationsController {

    private final VariationService variationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VariationResponse>> getProductById(@PathVariable String id) {
        try {
            VariationResponse response = variationService.getVariationById(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Variation retrieved successfully", response));
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
    public ResponseEntity<ApiResponse<VariationResponse>> create(@RequestBody CreateVariationRequest banner) {
        try {
            VariationResponse response = variationService.createVariation(banner);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Variation created successfully", response));
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

    @PostMapping("/find-by-ids")
    public ResponseEntity<ApiResponse<List<VariationResponse>>> findByIds(@RequestBody List<String> ids) {
        try {
            List<VariationResponse> response = variationService.findManyByIds(ids);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Variation retrieved successfully", response));
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

    @GetMapping("/find-by-product-id/{id}")
    public ResponseEntity<ApiResponse<List<VariationResponse>>> findByProductIds(@PathVariable String id) {
        try {
            List<VariationResponse> response = variationService.findManyByProductId(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(HttpStatus.OK.value(), "Variation retrieved successfully", response));
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
