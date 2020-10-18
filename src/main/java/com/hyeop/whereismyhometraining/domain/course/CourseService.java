package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> list() {
        List<Course> courseList = courseRepository.findAll();
        return courseList;
    }
}
