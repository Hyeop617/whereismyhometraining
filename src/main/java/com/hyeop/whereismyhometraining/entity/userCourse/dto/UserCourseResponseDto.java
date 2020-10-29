package com.hyeop.whereismyhometraining.entity.userCourse.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCourseResponseDto {
    private Long id;

    private Long courseId;

    private String title;

    private String type;

    private String description;

    private Integer level;

    private Integer period;

    private Integer progress;

    private LocalDate createDate;

    private LocalDate modifyDate;
}
