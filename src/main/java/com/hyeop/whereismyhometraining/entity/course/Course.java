package com.hyeop.whereismyhometraining.entity.course;

import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String type;

    private String description;

    private Integer level;

    private String gender;

    private Integer period;

    @OneToMany(mappedBy = "course")
    private List<CourseDetail> courseDetails = new ArrayList<>();
}
