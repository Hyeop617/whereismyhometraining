package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<Cookie> accessCookie = Optional.ofNullable(cookieProvider.getCookie((HttpServletRequest) request, "accessToken"));

            if (accessCookie.isPresent()) {
                String accessToken = accessCookie.get().getValue();
                // 기간이 유효한 토큰인지 확인
                if (jwtProvider.validateToken(accessToken)) {
                    // 토큰이 유효하면 토큰으로부터 유저정보 받아옴
                    Authentication authentication = jwtProvider.getAuthentication(accessToken);
                    // SecurityContext에 Authentication 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                // 기간이 유효한 토큰이 아닐 시
                } else {
                    Optional.ofNullable(cookieProvider.getCookie((HttpServletRequest) request, "refreshToken"))
                            .ifPresent(cookie -> {
                                String refreshToken = cookie.getValue();
                                String username = jwtProvider.getUsername(refreshToken);
                                String sns = jwtProvider.getSns(refreshToken);
                                if(!redisUtil.getData(sns+username).isEmpty()){
                                    Authentication authentication = jwtProvider.getAuthentication(refreshToken);
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                    Account account = Account.builder().username(username).sns(Sns.valueOf(sns)).build();
                                    String newAccessToken = jwtProvider.createToken(account, JwtProvider.TOKEN_VALID_TIME);
                                    response.addCookie(cookieProvider.createCookie("accessToken", newAccessToken));
                                }

                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
