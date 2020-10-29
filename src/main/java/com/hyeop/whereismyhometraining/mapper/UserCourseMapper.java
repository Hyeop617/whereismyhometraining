package com.hyeop.whereismyhometraining.mapper;

import com.hyeop.whereismyhometraining.entity.userCourse.UserCourse;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCourseMapper {
    UserCourseMapper INSTANCE = Mappers.getMapper(UserCourseMapper.class);

    @Mapping(source = "userCourse.course.id", target = "courseId")
    @Mapping(source = "userCourse.course.title", target = "title")
    @Mapping(source = "userCourse.course.type", target = "type")
    @Mapping(source = "userCourse.course.description", target = "description")
    @Mapping(source = "userCourse.course.level", target = "level")
    @Mapping(source = "userCourse.course.period", target = "period")
    @Mapping(target = "createDate", expression = "java(userCourse.getCreateDate().toLocalDate())")
    @Mapping(target = "modifyDate", expression = "java(userCourse.getModifyDate().toLocalDate())")
    @Mapping(target = "progress", expression = "java(((userCourse.getDay() - 1 ) * 100) / 30)")
    UserCourseResponseDto toDto(UserCourse userCourse);
}
