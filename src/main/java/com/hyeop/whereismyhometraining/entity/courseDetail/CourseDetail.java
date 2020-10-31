package com.hyeop.whereismyhometraining.entity.courseDetail;

import com.hyeop.whereismyhometraining.entity.BaseTimeEntity;
import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailModifyRequestDto;
import com.hyeop.whereismyhometraining.entity.workout.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CourseDetail extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer day;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private Integer workoutSet;

    private Integer workoutCount;

    private Integer workoutTime;

    private Integer workoutOrder;

    public void change(CourseDetailModifyRequestDto dto) {
        workoutCount = dto.getWorkoutCount();
        workoutTime = dto.getWorkoutTime();
        workoutOrder = dto.getWorkoutOrder();
        workoutSet = dto.getWorkoutSet();
        day = dto.getDay();
    }
}
