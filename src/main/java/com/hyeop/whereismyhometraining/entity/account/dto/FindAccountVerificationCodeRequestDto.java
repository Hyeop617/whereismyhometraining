package com.hyeop.whereismyhometraining.entity.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindAccountVerificationCodeRequestDto {

    private Boolean findPassword;

    private String email;

    private Integer code;
}
