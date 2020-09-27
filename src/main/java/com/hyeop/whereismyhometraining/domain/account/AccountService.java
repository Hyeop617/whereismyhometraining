package com.hyeop.whereismyhometraining.domain.account;

import com.hyeop.whereismyhometraining.config.CookieProvider;
import com.hyeop.whereismyhometraining.config.JwtProvider;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.account.dto.LoginRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.*;

@Service
@Slf4j
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CookieProvider cookieProvider;

    private AccountMapper accountMapper;

    private List<GrantedAuthority> createAuthoritiesList(Account account){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(Role.USER.getAuth().equals(account.getRoleAuth())){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (Role.ADMIN.getAuth().equals(account.getRoleAuth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    public ResponseEntity login(LoginRequestDto dto) {
        Map<String, Object> claims = new HashMap<>();
        Optional<Account> account = accountRepository.findByUsername(dto.getUsername());
        if(account.isPresent()){
            if(!passwordEncoder.matches(dto.getPassword(),account.get().getPassword())){
                log.info("비밀번호 달라요.");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            claims.put("id", account.get().getId());
            claims.put("username", account.get().getUsername());
            claims.put("role", createAuthoritiesList(account.get()));
            String token = jwtProvider.createToken("login", claims);
            ResponseCookie accessToken = cookieProvider.createCookie("accessToken", token);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessToken.toString()).body(jwtProvider.createToken("login", claims));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " 사용자가 없습니다."));
        List<GrantedAuthority> authorities = createAuthoritiesList(account);
        return new User(account.getUsername(), account.getPassword(), authorities);

    }

    public ResponseEntity signup(SignupRequestDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("dto is : {} ", dto);
        accountRepository.save(accountMapper.INSTANCE.toAccount(dto));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity usernameDuplCheck(String username) {
        return accountRepository.existsByUsername(username)
                ? new ResponseEntity<>(HttpStatus.CONFLICT)
                : new ResponseEntity<>(HttpStatus.OK);
    }
}