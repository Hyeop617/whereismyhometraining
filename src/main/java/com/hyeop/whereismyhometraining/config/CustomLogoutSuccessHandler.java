package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CookieProvider cookieProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Optional<Cookie> accessCookie = Optional.ofNullable(cookieProvider.getCookie((HttpServletRequest) request, "accessToken"));
        Optional<Cookie> refreshCookie = Optional.ofNullable(cookieProvider.getCookie((HttpServletRequest) request, "refreshToken"));

        if(accessCookie.isPresent()){
            log.info("쿠키 발견");
            String accessToken = accessCookie.get().getValue();

            String username = jwtProvider.getUsername(accessToken);
            String sns = jwtProvider.getSns(accessToken);

            Account account = Account.builder()
                                    .username(username)
                                    .sns(Sns.valueOf(sns))
                                    .build();
            String newToken = jwtProvider.createToken(account, 0L);

            accessCookie.get().setMaxAge(0);                                            // 쿠키 제거
            if(refreshCookie.isPresent()){
                refreshCookie.get().setMaxAge(0);
                response.addCookie(refreshCookie.get());
            }
            response.addCookie(accessCookie.get());
            redisUtil.setDataExpire(sns + username, newToken, 1L);              // 레디스 키 제거
        }
        response.sendRedirect("/");
    }
}
