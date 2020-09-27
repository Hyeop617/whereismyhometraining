package com.hyeop.whereismyhometraining.advice;

import com.hyeop.whereismyhometraining.advice.exception.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ExceptionController {

    @GetMapping("/403")
    public ResponseResult entrypointException(){
        throw new CAuthenticationEntryPointException("권한이 없습니다.");
    }
}
