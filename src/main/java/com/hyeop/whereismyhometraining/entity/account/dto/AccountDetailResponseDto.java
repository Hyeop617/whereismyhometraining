package com.hyeop.whereismyhometraining.entity.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetailResponseDto {

    private String nickname;        // 이름

    private String gender;          // 성별

    private Integer age;            // 연령대
}
