package com.hyeop.whereismyhometraining.domain.courseDetail;

import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailDeleteRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailCreateRequestDto;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDetailFacade {

    @Autowired
    private CourseDetailService courseDetailService;

    public CourseDetailResponseDto view(Long id, Integer day, Integer order) {
        return courseDetailService.get(id, day, order);
    }

    public List<CourseDetailResponseDto> list(Long id){
        return courseDetailService.list(id);
    }

    public ResponseEntity create(CourseDetailCreateRequestDto dto) {
        return courseDetailService.create(dto);
    }

    public ResponseEntity delete(CourseDetailDeleteRequestDto id) {
        return courseDetailService.delete(id);
    }
}
