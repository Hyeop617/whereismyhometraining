package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseFacade {

    private final CourseService courseService;

    public List<Course> list() {
        return courseService.list();
    }
}
