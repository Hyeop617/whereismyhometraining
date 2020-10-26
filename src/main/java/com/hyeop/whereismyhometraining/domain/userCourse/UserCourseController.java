package com.hyeop.whereismyhometraining.domain.userCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserCourseController {

    @Autowired
    private UserCourseFacade userCourseFacade;
}
