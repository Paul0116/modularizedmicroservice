package com.modularizedmicroservice.productservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Products {
    @Id
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
