package com.example.order.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDto {

    private String name;

    private int count;

    private int orderItemPrice;
}
