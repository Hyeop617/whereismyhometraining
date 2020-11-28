package com.hyeop.whereismyhometraining.domain.login;

import com.hyeop.whereismyhometraining.entity.RedisUtil;
import com.hyeop.whereismyhometraining.entity.account.dto.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginFacade loginFacade;

    private final RedisUtil redisUtil;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/workout/today";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "/login/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login/find")
    public String find(){
        return "/login/find";
    }

    @GetMapping("/login/reset")
    public String resetPassword(@RequestParam String token, Model model){
        String data = Optional.ofNullable(redisUtil.getData(token)).orElseThrow(() -> new AccessDeniedException("권한이 없습니다."));
        model.addAttribute("token", token);
        return "/login/resetPassword";
    }

    @PostMapping("/login/reset")
    @ResponseBody
    public ResponseResult resetPassword(@RequestBody ResetPasswordRequestDto dto){
        System.out.println(dto.toString());
        return loginFacade.resetPassword(dto);
    }

    @PostMapping("/login/find/sendVerificationCode")
    @ResponseBody
    public ResponseResult sendVerificationCode(@RequestBody FindAccountRequestDto dto){
        ResponseResult responseResult = loginFacade.sendVerificationCode(dto);
        System.out.println(responseResult.getData());
        return responseResult;
    }

    @PostMapping("/login/find/checkVerificationCode")
    @ResponseBody
    public ResponseResult checkVerificationCode(@RequestBody FindAccountVerificationCodeRequestDto dto){
        ResponseResult responseResult = loginFacade.checkVerificationCode(dto);
        return responseResult;
    }



    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        log.info("dto is {}", dto.toString());
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

    @GetMapping("/test")
    @ResponseBody
    public String test(@RequestParam Long userid){
        loginFacade.test(userid);
        return "wow";
    }

    @GetMapping("/fetchtest")
    @ResponseBody
    public String fetchtest(@RequestParam Long userid){
        loginFacade.fetchtest(userid);
        return "wow";
    }

}
