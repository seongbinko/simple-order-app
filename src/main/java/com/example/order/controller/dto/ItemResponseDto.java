package com.example.order.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponseDto {

    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String imgUrl;

    private String status;
}
