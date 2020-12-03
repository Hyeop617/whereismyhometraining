package com.hyeop.whereismyhometraining.entity.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Workout {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String type;

    private String description;

    private String youtubePath;

    private String imgPath;

    private Double met;           // met (칼로리 공식)

    private Double perSeconds;    // 회당 소모 시간

}
