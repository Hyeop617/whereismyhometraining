package com.hyeop.whereismyhometraining.entity.userCalories;

import com.hyeop.whereismyhometraining.entity.BaseTimeEntity;
import com.hyeop.whereismyhometraining.entity.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCalories extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String workoutType;

    private Double calories;
}
