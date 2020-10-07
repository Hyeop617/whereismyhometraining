package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.domain.account.AccountService;
import com.hyeop.whereismyhometraining.domain.account.AccountSnsService;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.accountSns.AccountSns;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {

    private  String secretKey = "whereIsMyHomeTraining";

    public final static Long TOKEN_VALID_TIME = 30 * 60 * 1000L;
    public final static Long REFRESH_TOKEN_VALID_TIME = 7 * 24 * 60 * 60 * 1000L;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountSnsService accountSnsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Boolean checkSnsAccount(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().containsKey("sns");
    }

    public String createAccessToken(String subject, Account account){
        return createToken(subject, account, TOKEN_VALID_TIME);
    }

    public String createRefreshToken(String subject, Account account){
        return createToken(subject, account, REFRESH_TOKEN_VALID_TIME);
    }

    public String createSnsAccessToken(String subject, AccountSns accountSns){
        return createSnsToken(subject, accountSns, TOKEN_VALID_TIME);
    }

    public String createSnsRefreshToken(String subject, AccountSns accountSns){
        return createSnsToken(subject, accountSns, REFRESH_TOKEN_VALID_TIME);
    }

    public String createToken(String subject, Account account, Long validTime){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", account.getUsername());
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createSnsToken(String subject, AccountSns accountSns, Long validTime){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", accountSns.getUsername());
        claims.put("sns", accountSns.getSns());
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = checkSnsAccount(token)
                                ? accountSnsService.loadUserByUsername(this.getUsername(token))
                                : accountService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("username").toString();
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
