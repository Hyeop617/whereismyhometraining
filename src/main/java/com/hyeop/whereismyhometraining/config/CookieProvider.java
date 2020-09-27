package com.hyeop.whereismyhometraining.config;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieProvider {

    public ResponseCookie createCookie(String cookieName, String value){
        return ResponseCookie.from(cookieName, value)
                        .httpOnly(false)
                        .maxAge(JwtProvider.TOKEN_VALID_TIME)
                        .path("/")
                        .build();

    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> reduce = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .map(Optional::ofNullable)
                .reduce(Optional.empty(), (a, b) -> a.isPresent() ^ b.isPresent() ? b : Optional.empty());
        return reduce.orElse(null);
    }
}
