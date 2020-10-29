package com.hyeop.whereismyhometraining.domain.admin;

import com.hyeop.whereismyhometraining.domain.course.CourseFacade;
import com.hyeop.whereismyhometraining.domain.courseDetail.CourseDetailFacade;
import com.hyeop.whereismyhometraining.domain.workout.WorkoutFacade;
import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailDeleteRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailCreateRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private CourseDetailFacade courseDetailFacade;

    @Autowired
    private CourseFacade courseFacade;

    @Autowired
    private WorkoutFacade workoutFacade;

    @GetMapping(value = {"/course", ""})
    public String viewCourse(Model model) {
        List<CourseResponseDto> courseList = courseFacade.listAll();
        List<WorkoutResponseDto> workoutList = workoutFacade.listAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("workoutList", workoutList);
        return "admin/course";
    }

    @PostMapping("/course/{id}")
    @ResponseBody
    public List<CourseDetailResponseDto> getDetails(@PathVariable Long id) {
        List<CourseDetailResponseDto> courseDetails = courseDetailFacade.list(id);
        log.info("detail : {} ", courseDetails.get(0).toString());
        return courseDetails;
    }

    @PostMapping("/course/add")
    @ResponseBody
    public ResponseEntity create(@RequestBody CourseDetailCreateRequestDto dto) {
        //TODO :: sout
        System.out.println(dto.getWorkoutTime());
        return courseDetailFacade.create(dto);
    }

    @PostMapping("/course/delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody CourseDetailDeleteRequestDto id) {
        return courseDetailFacade.delete(id);

    }
}
