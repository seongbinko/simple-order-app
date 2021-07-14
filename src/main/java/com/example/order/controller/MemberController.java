package com.example.order.controller;

import com.example.order.controller.dto.LoginRequestDto;
import com.example.order.controller.dto.SignUpRequestDto;
import com.example.order.service.MemberService;
import com.example.order.validator.LoginRequestValidator;
import com.example.order.validator.SignUpRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final SignUpRequestValidator signUpRequestValidator;
    private final LoginRequestValidator loginRequestDtoValidator;

    @InitBinder("signUpRequestDto")
    public void signupBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpRequestValidator);
    }

    @InitBinder("loginRequestDto")
    public void loginBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginRequestDtoValidator);
    }

    @PostMapping("api/auth/local/new")
    public ResponseEntity signUp(@Valid SignUpRequestDto signUpRequestDto, Errors errors){
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        memberService.signup(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/auth/local")
    public ResponseEntity login(@Valid LoginRequestDto loginRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok().body(memberService.login(loginRequestDto));
    }
}
