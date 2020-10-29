package com.hyeop.whereismyhometraining.entity.userCourse.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCourseRequestDto {
    private Long courseId;

    private Integer day;

    private Integer workoutOrder;                  // 진행 중인 당일 운동 순서
}
