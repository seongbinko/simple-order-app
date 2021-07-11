package com.example.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ItemResponseDto {

    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String imgUrl;

    private String status;
}
