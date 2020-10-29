package com.hyeop.whereismyhometraining.domain.workout;

import com.hyeop.whereismyhometraining.entity.workout.Workout;
import com.hyeop.whereismyhometraining.entity.workout.WorkoutRepository;
import com.hyeop.whereismyhometraining.entity.workout.dto.WorkoutResponseDto;
import com.hyeop.whereismyhometraining.mapper.WorkoutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    public static LocalDate TODAY = LocalDate.of(2020, 10, 29);

    @Autowired
    private WorkoutRepository workoutRepository;

    public HashMap<String, List<WorkoutResponseDto>> list() {
        List<WorkoutResponseDto> all = workoutRepository.findAllByType("all").stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
        List<WorkoutResponseDto> upperList = workoutRepository.findAllByType("upper").stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
        List<WorkoutResponseDto> lowerList = workoutRepository.findAllByType("lower").stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
        List<WorkoutResponseDto> coreList = workoutRepository.findAllByType("core").stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
        HashMap<String, List<WorkoutResponseDto>> list = new HashMap<>();
        list.put("하체 운동", lowerList);
        list.put("복근 운동", coreList);
        list.put("상체 운동", upperList);
        list.put("전신 운동", all);
        return list;
    }


    public List<WorkoutResponseDto> listAll() {
        return workoutRepository.findAll().stream().map(WorkoutMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public WorkoutResponseDto getRandom() {
        List<Workout> workouts = workoutRepository.findAll();
        if(!TODAY.equals(LocalDate.now())){
            WorkoutService.TODAY = LocalDate.now();
        }
        int size = workouts.size();
        int random = ((TODAY.getDayOfYear() * TODAY.getDayOfMonth() * TODAY.getMonthValue() * TODAY.getYear()) % size) + 1;
        if(random == 69){           // 69는 휴식으로 등록되어있어서 휴식은 제외..
            random = (random * TODAY.getDayOfYear()) % 69;
        }
        Workout workout = workouts.get(random);
        return WorkoutMapper.INSTANCE.toDto(workout);
    }
}
