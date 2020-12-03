package com.hyeop.whereismyhometraining.domain.workout;

import com.hyeop.whereismyhometraining.domain.common.CommonUtil;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workout")
@Slf4j
public class WorkoutController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WorkoutFacade workoutFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model) {
        Map<String, List<WorkoutResponseDto>> workoutList = workoutFacade.list();
        model.addAttribute("list", workoutList);
        return "workout/list";
    }

    @GetMapping("/today")
    public String today(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        WorkoutResponseDto responseDto = workoutFacade.getRandom();
        model.addAttribute("shapeImgPath", commonUtil.checkAccountShpaeImgPath());
        model.addAttribute("shapeMessage", commonUtil.checkAccountShapeMessage());
        model.addAttribute("workout", responseDto);
        model.addAttribute("isAnonymous", principal.toString().equals("anonymousUser"));
        return "workout/today";
    }

}
