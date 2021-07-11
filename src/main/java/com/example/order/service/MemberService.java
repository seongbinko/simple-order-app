package com.example.order.service;

import com.example.order.config.jwt.JwtTokenProvider;
import com.example.order.controller.dto.LoginRequestDto;
import com.example.order.controller.dto.SignUpRequestDto;
import com.example.order.entity.Member;
import com.example.order.entity.MemberRole;
import com.example.order.reposiroty.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(SignUpRequestDto signUpRequestDto) {
        memberRepository.save(
                Member.builder()
                        .nickname(signUpRequestDto.getNickname())
                        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .role(MemberRole.ROLE_USER)
                        .build()
        );
    }

    public Map<String, String> login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByNickname(loginRequestDto.getNickname()).orElse(null);
        String token = jwtTokenProvider.createToken(member.getNickname(), member.getRole().toString());
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", token);
        return map;
    }

}
