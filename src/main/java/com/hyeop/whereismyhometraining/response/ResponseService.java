package com.hyeop.whereismyhometraining.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public <T> ResponseResult getResult(T data){
        return ResponseResult.builder().code(0).message("성공").data(data).success(true).build();
    }

    public ResponseResult getFailResult() {
        return ResponseResult.builder().code(1).message("실패").success(false).build();
    }
}
