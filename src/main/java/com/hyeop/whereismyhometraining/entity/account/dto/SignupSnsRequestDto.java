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
    private Integer upperLevel;        // 상체 레벨 (-1 0 1)
    private Integer coreLevel;         // 복부 레벨 (-1 0 1)
    private Integer lowerLevel;        // 하체 레벨 (-1 0 1)
}