package com.example.order.controller.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class OrderRequestDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Integer orderItemPrice;

    @NotNull
    private Integer orderItemCount;
}
