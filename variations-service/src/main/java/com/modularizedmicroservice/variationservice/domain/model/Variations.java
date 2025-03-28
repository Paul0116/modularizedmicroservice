package com.modularizedmicroservice.variationservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "variations")
public class Variations {
    @Id
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
