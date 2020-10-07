package com.hyeop.whereismyhometraining.entity.accountSns;


import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class AccountSns {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;          // UID

    private String nickname;        // 이름

    private String gender;          // 성별

    private Integer age;            // 연령대

    private Integer level;          // 운동 경험

    @Enumerated(EnumType.STRING)
    private Role role;              // 역할

    @Enumerated(EnumType.STRING)
    private Sns sns;                // SNS 종류

}
