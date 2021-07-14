package com.example.order.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequestDto {

    @NotBlank
    @Length(min = 3, max= 10)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,10}$")
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Length(min = 8, max = 20)
    private String passwordConfirm;
}
