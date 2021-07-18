package com.example.order.validator;

import com.example.order.controller.dto.LoginRequestDto;
import com.example.order.entity.Member;
import com.example.order.reposiroty.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginRequestValidator implements Validator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequestDto loginRequestDto = (LoginRequestDto) target;

        if(!memberRepository.existsByNickname(loginRequestDto.getNickname())){
            errors.rejectValue("nickname", "invalid.nickname","존재하지 않는 닉네임입니다.");
        } else {
            Member member = memberRepository.findByNickname(loginRequestDto.getNickname()).get();
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
                errors.rejectValue("password", "invalid.password","패스워드가 일치하지 않습니다.");
            }
        }
    }
}
