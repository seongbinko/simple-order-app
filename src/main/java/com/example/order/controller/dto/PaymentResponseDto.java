package com.example.order.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PaymentResponseDto {

    private String createdAt;

    private int totalPrice;
}
