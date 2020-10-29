package com.hyeop.whereismyhometraining.domain.profile;

import com.hyeop.whereismyhometraining.config.JwtProvider;
import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditRequestDto;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import com.hyeop.whereismyhometraining.mapper.AccountMapper;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseResult getAccount() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        Optional<String> data = Optional.ofNullable(redisUtil.getData("EditAccess" + account.getUsername()));
        if(account.getRole().equals(Role.USER)){
            if(data.isEmpty()){
                return responseService.getFailResult();
            }
            String editAccessToken = jwtProvider.createProfileEditToken(account);
            redisUtil.setDataExpire("EditAccess" + account.getUsername(), editAccessToken, 5 * 60 * 1000L);
        }
        return responseService.getResult(AccountMapper.INSTANCE.toProfileEditResponseDto(account));
    }

    public ResponseEntity edit(ProfileEditRequestDto dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        log.info("dto is {}", dto.toString());
        if(dto.hasPassword()){
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        account.changeProfile(dto);
        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity check(String password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        if(!passwordEncoder.matches(password,account.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        String editAccessToken = jwtProvider.createProfileEditToken(account);
        redisUtil.setDataExpire("EditAccess" + account.getUsername(), editAccessToken, 5 * 60 * 1000L);
        return ResponseEntity.ok().build();
    }
}
