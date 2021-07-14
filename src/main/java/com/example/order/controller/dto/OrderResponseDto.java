package com.example.order.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponseDto {

    private Long id;

    private int orderPrice;

    private String createdAt;

    private List<OrderItemResponseDto> orderItemResponseDtos;
}
