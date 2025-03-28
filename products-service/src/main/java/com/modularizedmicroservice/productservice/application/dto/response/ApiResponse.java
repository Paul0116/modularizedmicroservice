package com.modularizedmicroservice.productservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer status;
    private String message;
    private T data;
}