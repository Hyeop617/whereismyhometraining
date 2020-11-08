package com.hyeop.whereismyhometraining.domain.login;

import com.hyeop.whereismyhometraining.domain.account.AccountService;
import com.hyeop.whereismyhometraining.entity.account.dto.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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

    public HashMap<String,String> getSnsSignupData(String regId, String uid) {
        return null;
    }

    public ResponseEntity signupSns(SignupRequestDto dto) {
        return accountService.signupSns(dto);
    }

    public ResponseEntity emailDuplCheck(String email) {
        return accountService.emailDuplCheck(email);
    }

    public ResponseResult sendVerificationCode(FindAccountRequestDto dto) {
        return accountService.sendVerificationCode(dto);
    }

    public ResponseResult checkVerificationCode(FindAccountVerificationCodeRequestDto dto) {
        return accountService.checkVerificationCode(dto);
    }

    public ResponseResult resetPassword(ResetPasswordRequestDto dto) {
        return accountService.resetPassword(dto);
    }
}
