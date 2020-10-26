package com.hyeop.whereismyhometraining.domain.courseDetail;

import com.hyeop.whereismyhometraining.domain.course.CourseFacade;
import com.hyeop.whereismyhometraining.domain.workout.WorkoutFacade;
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

    @Autowired
    private WorkoutFacade workoutFacade;

    @GetMapping("/")
    public String list(){
        courseFacade.list();
        return "/courseDetail/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id,
                       @RequestParam(defaultValue = "1") Integer day,
                       @RequestParam(defaultValue = "1") Integer order,
                       Model model)
    {
        System.out.println("day" + day);
        System.out.println("order" + order);
        CourseDetailResponseDto view1 = courseDetailFacade.view(id, day, order);
        System.out.println("============================");
        System.out.println(view1);
        System.out.println("============================");
        model.addAttribute("courseDetail", view1);
        return "courseDetail/view";
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CourseDetailResponseDto getDetail(@PathVariable Long id,
                                             @RequestParam (defaultValue = "1") Integer day,
                                             @RequestParam (defaultValue = "1") Integer order)
    {
        CourseDetailResponseDto courseDetail = courseDetailFacade.view(id, day, order);
        log.info("detail : {} ", courseDetail.toString());
        return courseDetail;
    }

}
