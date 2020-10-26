package com.hyeop.whereismyhometraining.domain.workout;

import com.hyeop.whereismyhometraining.entity.workout.Workout;
import com.hyeop.whereismyhometraining.entity.workout.WorkoutRepository;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import com.hyeop.whereismyhometraining.mapper.WorkoutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    public HashMap<String, List<Workout>> list() {
        List<Workout> all = workoutRepository.findAllByType("all");
        List<Workout> upperList = workoutRepository.findAllByType("upper");
        List<Workout> lowerList = workoutRepository.findAllByType("lower");
        List<Workout> coreList = workoutRepository.findAllByType("core");
        HashMap<String, List<Workout>> list = new HashMap<>();
        list.put("upperList", upperList);
        list.put("lowerList", lowerList);
        list.put("coreList", coreList);
        list.put("all", all);
        return list;
    }


    public List<WorkoutResponseDto> listAll() {
        return workoutRepository.findAll().stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
