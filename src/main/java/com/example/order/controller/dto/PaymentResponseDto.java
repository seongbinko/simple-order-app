package com.example.order.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class PaymentResponseDto {

    private String createdAt;

    private int totalPrice;
}
