package com.hyeop.whereismyhometraining.entity.account;

import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;        // 이메일

    private String password;        // 비밀번호

    private String email;        // 이메일

    private String nickname;        // 이름

    private String gender;          // 성별

    private Integer age;            // 연령대

    private Integer level;          // 운동 경험

    @Enumerated(EnumType.STRING)
    private Sns sns;                // SNS 종류

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "account")
    private List<UserCourse> userCourse = new ArrayList<>();

    public String getRoleAuth(){
        return this.role.getAuth();
    }
}
