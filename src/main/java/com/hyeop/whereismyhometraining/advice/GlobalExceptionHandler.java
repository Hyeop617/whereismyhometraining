package com.hyeop.whereismyhometraining.advice;

import com.hyeop.whereismyhometraining.advice.exception.*;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler implements AccessDeniedHandler {

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
    protected ResponseResult userNotFoundException(HttpServletRequest req, Exception e){
        ResponseResult failResult = responseService.getFailResult();
        failResult.setMessage(e.getMessage());
        return failResult;
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public String authenticaitonEntryPointException(HttpServletRequest req, Exception e, Model model){
        ResponseResult failResult = responseService.getFailResult();
        failResult.setMessage(e.getMessage());
        model.addAttribute("result", failResult);
        return "error/error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessEntryPointException(HttpServletRequest req, Exception e, Model model){
        ResponseResult failResult = responseService.getFailResult();
        failResult.setMessage(e.getMessage());
        model.addAttribute("result", failResult);
        return "error/error";
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect("/error/403");
    }
}
