package com.hyeop.whereismyhometraining.entity.userCalories.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCaloriesResponseDto {

    private Double totalCalories;
    private Double upperCalories;
    private Double lowerCalories;
    private Double allCalories;
    private Double coreCalories;

}
