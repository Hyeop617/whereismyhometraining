package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model){
        Map<String, List<CourseResponseDto>> list = courseFacade.list();
        model.addAttribute("upperList", list.get("upperList"));
        model.addAttribute("lowerList", list.get("lowerList"));
        model.addAttribute("coreList", list.get("coreList"));
        return "course/list";
    }
}
