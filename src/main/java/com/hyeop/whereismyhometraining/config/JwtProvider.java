package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.domain.account.AccountService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {

    private  String secretKey = "whereIsMyHomeTraining";

    public final static Long TOKEN_VALID_TIME = 30 * 60 * 1000L;

    @Autowired
    private AccountService accountService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String subject, Map<String, Object> claims){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = accountService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("username").toString();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("AUTH-TOKEN");
    }

    public Boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        }catch (Exception e){
            return false;
        }
    }





}
