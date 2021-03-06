package com.hyeop.whereismyhometraining.entity.userCourse;

import com.hyeop.whereismyhometraining.entity.BaseTimeEntity;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.course.Course;
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
public class UserCourse extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer day;

    private Integer workoutOrder;                  // 진행 중인 당일 운동 순서

    private Boolean isFinish;                // 완료 여부 (yes : 완료, no : 진행 중)

    public void changeDay(Integer day) {
        this.day = day;
    }

    public void changeOrder(Integer order){
        this.workoutOrder = order;
    }

    public void changeFinish(Boolean bool){
        this.isFinish = bool;
    }

}
