package com.hyeop.whereismyhometraining.entity.account;

import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;        // 아이디

    private String password;        // 비밀번호

    private String nickname;        // 이름

    private String gender;          // 성별

    private Integer age;            // 연령대

    private Integer level;          // 운동 경험

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRoleAuth(){
        return this.role.getAuth();
    }
}
