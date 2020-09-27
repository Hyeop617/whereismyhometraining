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

    private String username;

    private String nickname;

    private String password;

    private String gender;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Sns sns;

    public String getRoleAuth(){
        return this.role.getAuth();
    }
}
