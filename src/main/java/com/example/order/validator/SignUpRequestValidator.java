package com.example.order.validator;

import com.example.order.controller.dto.SignUpRequestDto;
import com.example.order.reposiroty.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpRequestValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequestDto signUpRequestDto = (SignUpRequestDto) target;

        if(memberRepository.existsByNickname(signUpRequestDto.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpRequestDto.getNickname()},"이미 사용중인 닉네임 입니다");
        }

        if(!signUpRequestDto.getPassword().equals(signUpRequestDto.getPasswordConfirm())) {
            errors.rejectValue("password", "wrong.value", "입력한 패스워드가 서로 일치하지 않습니다.");
        }
    }
}