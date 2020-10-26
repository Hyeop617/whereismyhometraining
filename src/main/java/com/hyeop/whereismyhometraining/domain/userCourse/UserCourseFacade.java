package com.hyeop.whereismyhometraining.domain.userCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCourseFacade {

    @Autowired
    private UserCourseService userCourseService;
}
