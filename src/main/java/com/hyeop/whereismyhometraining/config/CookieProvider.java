package com.hyeop.whereismyhometraining.config;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieProvider {

    public ResponseCookie createResponseCookie(String cookieName, String value){
        return ResponseCookie.from(cookieName, value)
                        .httpOnly(true)
                        .maxAge(cookieName.equals("refreshToken") ? JwtProvider.REFRESH_TOKEN_VALID_TIME / 1000 : JwtProvider.TOKEN_VALID_TIME / 1000)
                        .path("/")
                        .build();

    }

    public Cookie createCookie(String cookieName, String value){
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (cookieName.equals("refreshToken") ? JwtProvider.REFRESH_TOKEN_VALID_TIME / 1000 : JwtProvider.TOKEN_VALID_TIME / 1000));
        cookie.setPath("/");
        return cookie;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        //TODO :: 쿠키가 없을수도 잇듬
        Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElse(new Cookie[]{});
        Optional<Cookie> reduce = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .map(Optional::ofNullable)
                .reduce(Optional.empty(), (a, b) -> a.isPresent() ^ b.isPresent() ? b : Optional.empty());
        return reduce.orElse(null);
    }
}
