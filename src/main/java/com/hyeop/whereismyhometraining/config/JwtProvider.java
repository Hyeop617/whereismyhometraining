package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.domain.account.AccountService;
import com.hyeop.whereismyhometraining.entity.account.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Boolean checkSnsAccount(String token){
        //TODO :: sout 지우기
        Object sns = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("sns");
        System.out.println("checkSnsAccount is ::: " + sns);
        return sns.equals("NONE");
    }

    public String createToken(Account account, Long validTime){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", account.getUsername());
        claims.put("sns", account.getSns());
        Date now = new Date();
        return Jwts.builder()
                .setSubject(account.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
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

    public String getSns(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("sns").toString();
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
