package com.hyeop.whereismyhometraining.entity.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileEditRequestDto {

    private String password;

    private String email;

    private String nickname;

    private String gender;

    private Integer age;

    private Integer height;

    private Integer weight;

    public Boolean hasPassword(){
        return password != null;
    }
}
