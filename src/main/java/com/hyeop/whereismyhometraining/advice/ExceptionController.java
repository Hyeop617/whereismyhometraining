package com.hyeop.whereismyhometraining.advice;

import com.hyeop.whereismyhometraining.advice.exception.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ExceptionController {

    @GetMapping("/401")
    public ResponseResult entrypointException(){
        throw new CAuthenticationEntryPointException("로그인이 필요합니다.");
    }

    @GetMapping("/403")
    public ResponseResult accessDeniedException(){
        throw new AccessDeniedException("권한이 없습니다.");
    }
}
