package com.hyeop.whereismyhometraining.domain.workout;

import com.hyeop.whereismyhometraining.entity.workout.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutFacade workoutFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model) {
        Map<String, List<Workout>> workoutList = workoutFacade.list();
        model.addAttribute("upperList", workoutList.get("upperList"));
        model.addAttribute("lowerList", workoutList.get("lowerList"));
        model.addAttribute("coreList", workoutList.get("coreList"));
        model.addAttribute("all", workoutList.get("all"));
        return "workout/list";
    }

}
