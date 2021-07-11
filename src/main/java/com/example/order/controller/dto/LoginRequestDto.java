package com.example.order.controller.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
