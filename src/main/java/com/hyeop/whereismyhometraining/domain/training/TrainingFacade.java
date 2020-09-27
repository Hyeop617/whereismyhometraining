package com.hyeop.whereismyhometraining.domain.training;

import com.hyeop.whereismyhometraining.entity.training.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingFacade {

    private final TrainingService trainingService;

    public List<Training> list() {
        return trainingService.list();
    }
}
