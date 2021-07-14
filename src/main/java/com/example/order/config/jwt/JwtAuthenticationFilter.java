package com.example.order.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Jwt가 유효한 토큰인지 인증하기 위한 Filter
 * Security 설정 시 UsernamePasswordAuthenticationFilter 앞에 셋팅
 * JwtTokenProvider로 검증이 끝난 Jwt로 부터 Account 정보를 받아와서
 * UsernamePasswordAuthenticationFilter로 전달
 */

// Todo list GenericFilterBean (abstract)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 Jwt를 받아온다.
        String token = getJwtFromRequest((HttpServletRequest) request);
        // 유효한 토큰인지 확인한다.
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로 부터 유저정보를 가져온다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
