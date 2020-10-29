package com.hyeop.whereismyhometraining.entity.courseDetail.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDetailResponseDto {

    private Long id;

    private Long courseId;

    private String courseTitle;

    private String courseDescription;

    private Integer courseLevel;

    private String courseGender;

    private Integer day;

    private Long workoutId;

    private String workoutTitle;

    private String workoutType;

    private String workoutDescription;

    private String workoutYoutubePath;

    private String workoutImgPath;

    private Integer workoutSet;

    private Integer workoutCount;

    private Integer workoutOrder;

    private Integer workoutTime;

    private Boolean isFirst;

    private Boolean isEnd;

    private Integer progressDay;
}
