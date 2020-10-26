package com.hyeop.whereismyhometraining.mapper;

import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetail;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.entity.workout.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Mapper
public interface CourseDetailMapper {

    CourseDetailMapper INSTANCE = Mappers.getMapper(CourseDetailMapper.class);

    @Mapping(source = "courseDetail.course.id", target = "courseId")
    @Mapping(source = "courseDetail.workout.id", target = "workoutId")
    @Mapping(source = "courseDetail.course.title", target = "courseTitle")
    @Mapping(source = "courseDetail.workout.title", target = "workoutTitle")
    @Mapping(source = "courseDetail.workout.type", target = "workoutType")
    @Mapping(source = "courseDetail.workout.youtubePath", target = "workoutYoutubePath")
    @Mapping(source = "courseDetail.workout.imgPath", target = "workoutImgPath")
    @Mapping(source = "courseDetail.workout.description", target = "workoutDescription")
    @Mapping(source = "courseDetail.course.description", target = "courseDescription")
    CourseDetailResponseDto toDto(CourseDetail courseDetail);


    default CourseDetail toEntity(CourseDetailRequestDto dto) {
        if(dto == null){
            return null;
        }

        return CourseDetail.builder()
                .course(Course.builder().id(dto.getCourseId()).build())
                .workout(Workout.builder().id(dto.getWorkoutId()).build())
                .day(dto.getDay())
                .workoutCount(dto.getWorkoutCount())
                .workoutOrder(dto.getWorkoutOrder())
                .workoutSet(dto.getWorkoutSet())
                .build();
    }
}

