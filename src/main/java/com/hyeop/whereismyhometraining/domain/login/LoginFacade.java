package com.hyeop.whereismyhometraining.domain.login;

import com.hyeop.whereismyhometraining.domain.account.AccountService;
import com.hyeop.whereismyhometraining.entity.account.dto.LoginRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final AccountService accountService;

    public ResponseEntity login(LoginRequestDto dto) {
        return accountService.login(dto);
    }

    public ResponseEntity signup(SignupRequestDto dto) {
        return accountService.signup(dto);
    }

    public ResponseEntity usernameDuplCheck(String username) {
        return accountService.usernameDuplCheck(username);
    }
}
