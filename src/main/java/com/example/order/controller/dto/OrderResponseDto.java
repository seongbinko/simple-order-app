package com.example.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;

    private int orderPrice;

    private String createdAt;

    private List<OrderItemResponseDto> orderItemResponseDtos;
}
