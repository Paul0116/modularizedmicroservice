package com.modularizedmicroservice.productservice.application.dto.response;

import com.modularizedmicroservice.productservice.application.dto.response.variation.VariationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String sku;
    private String name;
    private String description;
    private String tags;
    private Integer category_id;
    private Integer status;
    private Boolean is_delete;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private List<VariationResponse> variations;
}
