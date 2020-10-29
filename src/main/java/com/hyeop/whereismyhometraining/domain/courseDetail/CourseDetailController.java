package com.hyeop.whereismyhometraining.domain.courseDetail;

import com.hyeop.whereismyhometraining.domain.course.CourseFacade;
import com.hyeop.whereismyhometraining.domain.workout.WorkoutFacade;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course/detail")
@Slf4j
public class CourseDetailController {

    @Autowired
    private CourseDetailFacade courseDetailFacade;

    @Autowired
    private CourseFacade courseFacade;

    @GetMapping("/")
    public String list() {
        courseFacade.list();
        return "/courseDetail/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id,
                       @RequestParam(defaultValue = "1") Integer day,
                       @RequestParam(defaultValue = "1") Integer order,
                       Model model) {
        CourseDetailResponseDto view1 = courseDetailFacade.view(id, day, order);
        model.addAttribute("courseDetail", view1);
        return "courseDetail/view";
    }

    @PostMapping("/")
    @ResponseBody
    public CourseDetailResponseDto getDetail(@RequestBody CourseDetailRequestDto dto) {
        CourseDetailResponseDto courseDetail = courseDetailFacade.view(dto.getCourseId(), dto.getDay(), dto.getOrder());
        return courseDetail;
    }

}
