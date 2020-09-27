package com.hyeop.whereismyhometraining.domain.login;

import com.hyeop.whereismyhometraining.entity.account.dto.LoginRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginFacade loginFacade;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        ResponseEntity<String> responseEntity = loginFacade.login(dto);
        return responseEntity;
    }

    @ResponseBody
    @PostMapping("/signup/execute")
    public ResponseEntity signupExecute(@RequestBody SignupRequestDto dto){
        ResponseEntity responseEntity = loginFacade.signup(dto);
        return responseEntity;
    }

    @ResponseBody
    @PostMapping("/signup/check")
    public ResponseEntity signupCheck(@RequestBody String username){
        ResponseEntity responseEntity = loginFacade.usernameDuplCheck(username);
        return responseEntity;
    }

}
