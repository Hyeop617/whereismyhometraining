package com.hyeop.whereismyhometraining.domain.userCalories;

import com.hyeop.whereismyhometraining.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/calories")
@Slf4j
public class UserCaloriesController {

    @Autowired
    private UserCaloriesFacade userCaloriesFacade;

    @PostMapping("/calc")
    @ResponseBody
    public ResponseResult calc(){
        return userCaloriesFacade.calc();
    }
}
