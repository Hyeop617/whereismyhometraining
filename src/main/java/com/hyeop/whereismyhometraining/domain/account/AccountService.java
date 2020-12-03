package com.hyeop.whereismyhometraining.domain.account;

import com.hyeop.whereismyhometraining.advice.exception.CUserNotFoundException;
import com.hyeop.whereismyhometraining.config.CookieProvider;
import com.hyeop.whereismyhometraining.config.JwtProvider;
import com.hyeop.whereismyhometraining.domain.mail.MailService;
import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.account.dto.*;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourse;
import com.hyeop.whereismyhometraining.mapper.AccountMapper;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CookieProvider cookieProvider;

    public List<GrantedAuthority> createAuthoritiesList(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (Role.USER.getAuth().equals(account.getRoleAuth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (Role.ADMIN.getAuth().equals(account.getRoleAuth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (Role.SNS.getAuth().equals(account.getRoleAuth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SNS"));
        }
        return authorities;
    }

    public ResponseEntity login(LoginRequestDto dto) {
        HashMap<String, ResponseEntity> hm = new HashMap<>();
        hm.put("result", new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        accountRepository.findByUsernameAndSns(dto.getUsername(), Sns.NONE).ifPresent(account -> {
            if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
                hm.put("result", new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
            } else {
                String accessToken = jwtProvider.createToken(account, JwtProvider.TOKEN_VALID_TIME);
                String refreshToken = jwtProvider.createToken(account, JwtProvider.REFRESH_TOKEN_VALID_TIME);
                // Token을 담은 Cookie 생성
                ResponseCookie accessCookie = cookieProvider.createResponseCookie("accessToken", accessToken);
                ResponseCookie refreshCookie = cookieProvider.createResponseCookie("refreshToken", refreshToken);
                // Redis에 Refresh Token 저장
                redisUtil.setDataExpire(account.getSns() + account.getUsername(), refreshToken, JwtProvider.REFRESH_TOKEN_VALID_TIME);
                hm.put("result", ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, accessCookie.toString(), refreshCookie.toString())
                        .body(accessToken)
                );
            }
        });
        return hm.get("result");
    }

    public void snsLogin(Sns sns, String username, HttpServletResponse response) {
        Account account = accountRepository.findByUsernameAndSns(username, sns).orElseThrow(() -> new CUserNotFoundException(username + " 사용자가 없습니다."));
        String accessToken = jwtProvider.createToken(account, JwtProvider.TOKEN_VALID_TIME);
        String refreshToken = jwtProvider.createToken(account, JwtProvider.REFRESH_TOKEN_VALID_TIME);
        // Token을 담은 Cookie 생성
        Cookie accessCookie = cookieProvider.createCookie("accessToken", accessToken);
        Cookie refreshCookie = cookieProvider.createCookie("refreshToken", refreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        // Redis에 Refresh Token 저장
        redisUtil.setDataExpire(sns + username, refreshToken, JwtProvider.REFRESH_TOKEN_VALID_TIME);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new CUserNotFoundException(username + " 사용자가 없습니다."));
        List<GrantedAuthority> authorities = createAuthoritiesList(account);
        return new User(account.getUsername(), account.getPassword(), authorities);

    }

    public ResponseEntity signup(SignupRequestDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        accountRepository.save(AccountMapper.INSTANCE.toAccount(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Boolean checkExist(Sns sns, String username) {
        return accountRepository.existsByUsernameAndSns(username, sns);
    }

    public ResponseEntity usernameDuplCheck(String username) {
        return accountRepository.existsByUsername(username)
                ? new ResponseEntity<>(HttpStatus.CONFLICT)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity signupSns(SignupRequestDto dto) {

        dto.setPassword(passwordEncoder.encode("cryptedhyeop123"));
        accountRepository.save(AccountMapper.INSTANCE.toAccount(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity emailDuplCheck(String email) {
        return accountRepository.existsByEmail(email)
                ? new ResponseEntity<>(HttpStatus.CONFLICT)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseResult sendVerificationCode(FindAccountRequestDto dto) {
        if (dto.getFindPassword().equals(true)) {
            Account account = accountRepository.findByEmailAndUsername(dto.getEmail(), dto.getUsername()).orElse(Account.builder().id(-1L).build());
            if (account.getId().equals(-1L)) {
                return responseService.getResult("not found");
            }
            mailService.sendVerificationMail(account.getEmail());
            return responseService.getResult("send");

        }
        Account account = accountRepository.findByEmail(dto.getEmail()).orElse(Account.builder().id(-1L).build());
        if (account.getId().equals(-1L)) {
            return responseService.getResult("not found");
        }
        mailService.sendVerificationMail(account.getEmail());
        return responseService.getResult("send");
    }


    public ResponseResult checkVerificationCode(FindAccountVerificationCodeRequestDto dto) {
        Optional<String> code = Optional.ofNullable(redisUtil.getData("verification-code" + dto.getEmail()));
        if (code.isPresent()) {
            if (code.get().equals(dto.getCode().toString())) {
                Optional<Account> account = accountRepository.findByEmail(dto.getEmail());
                String encode = passwordEncoder.encode(account.get().getUsername());
                FindAccountVerificationResponseDto responseDto = FindAccountVerificationResponseDto
                        .builder()
                        .findPassword(dto.getFindPassword())
                        .username(account.get().getUsername())
                        .redirectPasswordUri(encode)
                        .build();
                redisUtil.setDataExpire("verification-code" + dto.getEmail(), "", 1L);     // 인증코드 만료시킴.
                redisUtil.setDataExpire(encode, account.get().getUsername(), 10 * 60L);      // 비밀번호 재설정 토큰 설정
                return responseService.getResult(responseDto);
            }
        }

        return responseService.getResult("false");
    }

    public ResponseResult resetPassword(ResetPasswordRequestDto dto) {
        String username = redisUtil.getData(dto.getToken());
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("이상하다"));
        account.changePassword(passwordEncoder.encode(dto.getPassword()));
        accountRepository.save(account);
        redisUtil.setDataExpire(dto.getToken(), "", 1L);
        return responseService.getResult("done");
    }

    public List<Account> test(Long userid) {
        List<Account> all = accountRepository.findAll();
        for(Account acc : all){
            List<UserCourse> userCourse = acc.getUserCourse();
            log.info("check : {} ", userCourse.size());
        }
        return all;
    }

    public List<Account>    fetchtest(Long userid) {
        List<Account> all = accountRepository.findAllJoinFetch();
        for(Account acc : all){
            log.info("check : {} ", acc.getUserCourse().size());
        }
        return all;
    }

    public AccountDetailResponseDto getAccountDetail(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        return AccountMapper.INSTANCE.toAccountDetailResponseDto(account);
    }

    public void update(String username, String email) {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("sdf"));
        account.changeEmail(email);
        accountRepository.save(account);
    }
}
