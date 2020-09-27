package com.hyeop.whereismyhometraining.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Cookie cookie = Optional.ofNullable(cookieProvider.getCookie((HttpServletRequest) request, "accessToken"))
                                         .orElseThrow(Exception::new);
            String accessToken = cookie.getValue();
            if (jwtProvider.validateToken(accessToken)) {           // 유효한 토큰인지 확인
                // 토큰이 유효하면 토큰으로부터 유저정보 받아옴
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                // SecurityContext에 Authentication 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }
}
