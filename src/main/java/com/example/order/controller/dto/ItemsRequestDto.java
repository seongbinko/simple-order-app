package com.example.order.controller.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class ItemsRequestDto {

    @Pattern(regexp = "^[^\\{\\}\\[\\]\\/?.,;:|\\)*~`!\\^\\\\\\-_+<>@#$%&'\\(=\"]{0,20}$")
    private String keyword;

    @Min(0) @Max(9)
    private int categoryId;
}
