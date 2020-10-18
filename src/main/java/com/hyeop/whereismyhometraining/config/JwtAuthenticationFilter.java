package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.advice.exception.*;
import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.accountSns.AccountSns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
                                String username = redisUtil.getData(refreshToken);
                                if (username.equals(jwtProvider.getUsername(refreshToken))) {
                                    Authentication authentication = jwtProvider.getAuthentication(refreshToken);
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                    addCookie(response, refreshToken, username);
//                                    if (jwtProvider.checkSnsAccount(refreshToken)) {
//                                        AccountSns accountSns = AccountSns.builder().username(username).build();
//                                        String newAccessToken = jwtProvider.createSnsAccessToken(username, accountSns);
//                                        response.addCookie(cookieProvider.createCookie("accessToken", newAccessToken));
//                                    } else {
//                                        Account account = Account.builder().username(username).build();
//                                        String newAccessToken = jwtProvider.createAccessToken(username, account);
//                                        response.addCookie(cookieProvider.createCookie("accessToken", newAccessToken));
//                                    }
                                }

                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void addCookie(HttpServletResponse response, String token, String username){
        if (jwtProvider.checkSnsAccount(token)) {
            AccountSns accountSns = AccountSns.builder().username(username).build();
            String newAccessToken = jwtProvider.createSnsAccessToken(username, accountSns);
            response.addCookie(cookieProvider.createCookie("accessToken", newAccessToken, false));
        } else {
            Account account = Account.builder().email(username).build();
            String newAccessToken = jwtProvider.createAccessToken(username, account);
            response.addCookie(cookieProvider.createCookie("accessToken", newAccessToken, false));
        }
    }
}
