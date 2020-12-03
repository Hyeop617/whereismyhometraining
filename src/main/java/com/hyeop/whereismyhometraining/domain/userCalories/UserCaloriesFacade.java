package com.hyeop.whereismyhometraining.domain.userCalories;

import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCaloriesFacade {

    @Autowired
    private UserCaloriesService userCaloriesService;

    public ResponseResult calc() {
        return userCaloriesService.calc();
    }
}
