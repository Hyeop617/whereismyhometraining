package com.hyeop.whereismyhometraining.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseResult<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;
}
