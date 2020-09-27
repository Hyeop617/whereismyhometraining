package com.hyeop.whereismyhometraining.entity.account.dto;

import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String username;
    private String nickname;
    private String password;
    private String gender;
    private Integer age;
    private Role role;
    private Sns sns;
}
