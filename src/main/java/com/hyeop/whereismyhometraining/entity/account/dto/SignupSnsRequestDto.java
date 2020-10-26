package com.hyeop.whereismyhometraining.entity.account.dto;

import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupSnsRequestDto {

    private String username;
    private String nickname;
    private String gender;
    private Integer age;
    private Sns sns;
    private Integer level;
}