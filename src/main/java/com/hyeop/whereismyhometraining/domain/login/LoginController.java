package com.hyeop.whereismyhometraining.domain.login;

import com.hyeop.whereismyhometraining.entity.account.dto.LoginRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupSnsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginFacade loginFacade;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
        return "index";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
        return "login";
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
    @PostMapping("/signup/execute/sns")
    public ResponseEntity signupExecuteSns(@RequestBody SignupRequestDto dto){
        System.out.println("signup");
        ResponseEntity responseEntity = loginFacade.signupSns(dto);
        return responseEntity;
    }

    @ResponseBody
    @PostMapping("/signup/checkUsername")
    public ResponseEntity usernameDuplCheck(@RequestBody String username){
        ResponseEntity responseEntity = loginFacade.usernameDuplCheck(username);
        return responseEntity;
    }

    @ResponseBody
    @PostMapping("/signup/checkEmail")
    public ResponseEntity emailDuplCheck(@RequestBody String email){
        ResponseEntity responseEntity = loginFacade.emailDuplCheck(email);
        return responseEntity;
    }

    @GetMapping("/signup/sns/{regId}/{uid}")
    public String signupThirdparty(@PathVariable String regId, @PathVariable String uid, Model model){
        System.out.println("uid is " + uid);
        model.addAttribute("regId", regId);
        model.addAttribute("uid", uid);
        return "snsSignup";
    }

}
