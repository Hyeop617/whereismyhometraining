package com.hyeop.whereismyhometraining.domain.workout;

import com.hyeop.whereismyhometraining.entity.workout.Workout;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class WorkoutFacade {

    @Autowired
    private WorkoutService workoutService;

    public HashMap<String, List<Workout>> list(){
        return workoutService.list();
    }

    public List<WorkoutResponseDto> listAll(){
        return workoutService.listAll();
    }
}
