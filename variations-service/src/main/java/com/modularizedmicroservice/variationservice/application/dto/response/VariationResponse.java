package com.modularizedmicroservice.variationservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class VariationResponse {
    private String id;
    private List image;
    private String size;
    private String weight;
    private String color;
    private Double price;
    private Double discounted_price;
    private String barcode;
    private Boolean no_expiration;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String product_id;
    private String tags;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private Boolean is_delete;
    private Integer status;
}
