package com.hyeop.whereismyhometraining.domain.courseDetail;

import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.CourseRepository;
import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetail;
import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetailRepository;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailDeleteRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.mapper.CourseDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDetailService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;

    public List<CourseDetailResponseDto> list(Long id) {
        // TODO :: 코스 예외처리
        Course course = courseRepository.findAllById(id)
                .orElseThrow(() -> new RuntimeException("코스가 없습니다."));
        List<CourseDetail> courseDetails = courseDetailRepository.findAllByCourseOrderByDayDescWorkoutOrderDesc(course);
        return courseDetails.stream().map(CourseDetailMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public CourseDetailResponseDto get(Long id, Integer day, Integer order) {
        // TODO :: 없는 코스 예외처리
        Course course = courseRepository.findAllById(id)
                .orElseThrow(() -> new RuntimeException("코스가 없습니다."));
        CourseDetail courseDetail = courseDetailRepository.findByCourseAndDayAndWorkoutOrder(course, day, order)
                .orElseThrow();
        return CourseDetailMapper.INSTANCE.toDto(courseDetail);

    }

    public ResponseEntity create(CourseDetailRequestDto dto) {
        CourseDetail courseDetail = CourseDetailMapper.INSTANCE.toEntity(dto);
        CourseDetail save = courseDetailRepository.save(courseDetail);
        return ResponseEntity.ok().body(save.getId());
    }

    public ResponseEntity delete(CourseDetailDeleteRequestDto id) {
        courseDetailRepository.deleteById(id.getId());
        return ResponseEntity.ok().build();
    }
}
