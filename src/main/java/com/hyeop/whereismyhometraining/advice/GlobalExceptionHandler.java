package com.hyeop.whereismyhometraining.advice;

import com.hyeop.whereismyhometraining.advice.exception.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseResult defaultException(HttpServletRequest req, Exception e){
        System.out.println("CAUTH");
        return responseService.getFailResult();
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseResult userNorFoundException(HttpServletRequest req, Exception e){
        ResponseResult failResult = responseService.getFailResult();
        failResult.setMessage(e.getMessage());
        return failResult;
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public String authenticaitonEntryPointException(HttpServletRequest req, Exception e, Model model){
        ResponseResult failResult = responseService.getFailResult();
        failResult.setMessage("다시 로그인 해주세요.");
        model.addAttribute("result", failResult);
        return "error/error";
    }
}
