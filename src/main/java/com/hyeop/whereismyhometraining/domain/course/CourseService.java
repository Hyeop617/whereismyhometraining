package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.CourseRepository;
import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import com.hyeop.whereismyhometraining.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public HashMap<String, List<CourseResponseDto>> list() {
        List<Course> upper = courseRepository.findAllByType("upper");
        List<Course> lower = courseRepository.findAllByType("lower");
        List<Course> core = courseRepository.findAllByType("core");
        HashMap<String, List<CourseResponseDto>> list = new HashMap<>();
        list.put("upperList", upper.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        list.put("lowerList", lower.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        list.put("coreList", core.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        return list;
    }

    public List<CourseResponseDto> listAll(){
        return courseRepository.findAll().stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
