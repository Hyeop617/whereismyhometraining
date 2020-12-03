package com.hyeop.whereismyhometraining.entity.workout.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutResponseDto {
    private Long id;

    private String title;

    private String type;

    private String description;

    private String youtubePath;

    private String imgPath;

    private Double met;           // met (칼로리 공식)

    private Double perSeconds;    // 회당 소모 시간
}
