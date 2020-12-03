package com.hyeop.whereismyhometraining.domain.userCourse;

import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseRequestDto;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseResponseDto;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCourseFacade {

    @Autowired
    private UserCourseService userCourseService;

    public ResponseEntity addUserCourse(UserCourseRequestDto dto) {
        return userCourseService.addUserCourse(dto);
    }

    public Map<String, List<UserCourseResponseDto>> list() {
        return userCourseService.list();
    }

    public CourseDetailResponseDto view(Long id) {
        return userCourseService.view(id);
    }

    public HashMap<String, ResponseResult> nextWorkout(UserCourseRequestDto dto) {
        return userCourseService.nextWorkout(dto);
    }

    public ResponseEntity addUserCourseGoogleCalendar(UserCourseRequestDto dto) {
        return userCourseService.addUserCourseGoogleCalendar(dto);
    }

    public ResponseEntity deleteUserCourseGoogleCalendar(UserCourseRequestDto dto) {
        return userCourseService.deleteUserCourseGoogleCalendar(dto);
    }
}
