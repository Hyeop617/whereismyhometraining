package com.hyeop.whereismyhometraining.mapper;

import com.hyeop.whereismyhometraining.entity.workout.Workout;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutMapper {
    WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

    WorkoutResponseDto toDto(Workout workout);

}
