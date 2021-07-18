package com.example.order.controller.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class ItemsRequestDto {

    private String keyword;

    @Min(0) @Max(9)
    private int categoryId;
}
