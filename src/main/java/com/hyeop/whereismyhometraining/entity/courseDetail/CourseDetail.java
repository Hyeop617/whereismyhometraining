package com.hyeop.whereismyhometraining.entity.courseDetail;

import com.hyeop.whereismyhometraining.entity.course.Course;
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
public class CourseDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer day;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private String workoutType;

    private Integer workoutSet;

    private Integer workoutCount;

    private Integer workoutTime;

    private Integer workoutOrder;
}
