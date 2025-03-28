package com.modularizedmicroservice.productservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateProductRequest {
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
}
