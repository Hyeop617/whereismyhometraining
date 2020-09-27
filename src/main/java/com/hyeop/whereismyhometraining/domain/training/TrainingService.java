package com.hyeop.whereismyhometraining.domain.training;

import com.hyeop.whereismyhometraining.entity.training.Training;
import com.hyeop.whereismyhometraining.entity.training.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<Training> list() {
        List<Training> trainingList = trainingRepository.findAll();
        return trainingList;
    }
}
