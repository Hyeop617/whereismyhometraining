package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseFacade {

    private final CourseService courseService;

    public Map<String, List<CourseResponseDto>> list() {
        return courseService.list();
    }

    public List<CourseResponseDto> listAll(){
        return courseService.listAll();
    }
}
