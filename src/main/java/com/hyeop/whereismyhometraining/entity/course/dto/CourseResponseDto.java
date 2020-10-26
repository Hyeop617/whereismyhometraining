package com.hyeop.whereismyhometraining.entity.course.dto;

import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;

    private String title;

    private String type;

    private String description;

    private Integer level;

    private String gender;

    private Integer period;

    private List<CourseDetail> courseDetails;
}
