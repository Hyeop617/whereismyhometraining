package com.hyeop.whereismyhometraining.entity.account.dto;

import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String username;
    private String email;
    private String nickname;
    private String password;
    private String gender;
    private Sns sns;
    private Integer age;
    private Integer level;
    private Integer upperLevel;        // 상체 레벨 (-1 0 1)
    private Integer coreLevel;         // 복부 레벨 (-1 0 1)
    private Integer lowerLevel;        // 하체 레벨 (-1 0 1)
}
