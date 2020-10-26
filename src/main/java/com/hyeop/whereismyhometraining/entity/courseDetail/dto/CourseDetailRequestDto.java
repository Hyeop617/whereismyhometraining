package com.hyeop.whereismyhometraining.entity.courseDetail.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CourseDetailRequestDto {
    private Long courseId;

    private Long workoutId;

    private Integer day;

    private Integer workoutSet;

    private Integer workoutCount;

    private Integer workoutOrder;
}
