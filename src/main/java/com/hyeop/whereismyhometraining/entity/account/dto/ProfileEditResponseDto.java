package com.hyeop.whereismyhometraining.entity.account.dto;

import com.hyeop.whereismyhometraining.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileEditResponseDto {

    private Role role;

    private String email;

    private String nickname;

    private String gender;

    private Integer age;

    private Integer level;
}
