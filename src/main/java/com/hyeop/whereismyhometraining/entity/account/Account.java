package com.hyeop.whereismyhometraining.entity.account;

import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditRequestDto;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourse;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;        // 아이디

    private String password;        // 비밀번호

    private String email;        // 이메일

    private String nickname;        // 이름

    private String gender;          // 성별

    private Integer age;            // 연령대

    private Integer level;          // 운동 경험

    private Integer upperLevel;        // 상체 레벨 (-1 0 1)

    private Integer coreLevel;         // 복부 레벨 (-1 0 1)

    private Integer lowerLevel;        // 하체 레벨 (-1 0 1)

    @Enumerated(EnumType.STRING)
    private Sns sns;                // SNS 종류

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "account")
    private List<UserCourse> userCourse = new ArrayList<>();

    public String getRoleAuth(){
        return this.role.getAuth();
    }

    public void changeProfile(ProfileEditRequestDto dto){
        email = dto.getEmail();
        age = dto.getAge();
        gender = dto.getGender();
        nickname = dto.getNickname();
        level = dto.getLevel();
        if(dto.hasPassword()){
            password = dto.getPassword();
        }
    }

    public void changeUpperLevel(Integer courseLevel){
        upperLevel += (courseLevel * 8);
    }

    public void changeCoreLevel(Integer courseLevel){
        coreLevel += (courseLevel * 8);
    }

    public void changeLowerLevel(Integer courseLevel){
        lowerLevel += (courseLevel * 8);
    }
}
