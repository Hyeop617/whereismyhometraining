package com.hyeop.whereismyhometraining.entity.courseDetail.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseDetailRequestDto {
    private Long courseId;
    private Integer day;
    private Integer order;
}
