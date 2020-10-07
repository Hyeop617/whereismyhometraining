package com.hyeop.whereismyhometraining.domain.account;

import com.hyeop.whereismyhometraining.advice.exception.CUserNotFoundException;
import com.hyeop.whereismyhometraining.config.CookieProvider;
import com.hyeop.whereismyhometraining.config.JwtProvider;
import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.accountSns.AccountSns;
import com.hyeop.whereismyhometraining.entity.accountSns.AccountSnsRepository;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountSnsService implements UserDetailsService {

    @Autowired
    private AccountSnsRepository accountSnsRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CookieProvider cookieProvider;

    @Autowired
    private RedisUtil redisUtil;

    public List<GrantedAuthority> createAuthoritiesList(AccountSns accountSns){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(Role.USER.getAuth().equals(accountSns.getRole().getAuth())){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (Role.ADMIN.getAuth().equals(accountSns.getRole().getAuth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountSns accountSns = accountSnsRepository.findByUsername(username).orElseThrow(() -> new CUserNotFoundException(username + " 사용자가 없습니다."));
        List<GrantedAuthority> authorities = createAuthoritiesList(accountSns);
        return new User(accountSns.getUsername(), "", authorities);
    }

    public Boolean checkExist(Sns sns, String username) {
        return accountSnsRepository.existsBySnsAndUsername(sns, username);
    }

    public void login(Sns sns, String username, HttpServletResponse response){
        AccountSns accountSns = accountSnsRepository.findBySnsAndUsername(sns, username).orElseThrow(() -> new CUserNotFoundException(username + " 사용자가 없습니다."));
        String accessToken = jwtProvider.createSnsAccessToken(sns + username, accountSns);
        String refreshToken = jwtProvider.createSnsRefreshToken(sns + username, accountSns);
        // Token을 담은 Cookie 생성
        Cookie accessCookie = cookieProvider.createCookie("accessToken", accessToken, false);
        Cookie refreshCookie = cookieProvider.createCookie("refreshToken", refreshToken, true);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        // Redis에 Refresh Token 저장
        redisUtil.setDataExpire(sns + username, refreshToken, JwtProvider.REFRESH_TOKEN_VALID_TIME);
    }
}
