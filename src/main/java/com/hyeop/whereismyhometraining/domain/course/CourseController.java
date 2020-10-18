package com.hyeop.whereismyhometraining.domain.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model){
        model.addAttribute("list", courseFacade.list());
        return "course/list";
    }
}
