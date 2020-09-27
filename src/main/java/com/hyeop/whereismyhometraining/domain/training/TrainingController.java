package com.hyeop.whereismyhometraining.domain.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingFacade trainingFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model){
        model.addAttribute("list", trainingFacade.list());
        return "training/list";
    }
}
