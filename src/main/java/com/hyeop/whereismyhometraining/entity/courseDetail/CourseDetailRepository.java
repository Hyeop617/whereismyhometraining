package com.hyeop.whereismyhometraining.entity.courseDetail;

import com.hyeop.whereismyhometraining.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {


    List<CourseDetail> findAllByCourseOrderByDayDescWorkoutOrderDesc(Course course);

    List<CourseDetail> findAllByCourseOrderByDay(Course course);

    List<CourseDetail> findAllByCourseAndDayOrderByDay(Course course, Integer day);

    Optional<CourseDetail> findByCourseAndDayAndWorkoutOrder(Course course, Integer day, Integer workoutOrder);

    Integer countByCourseAndDay(Course course, Integer day);
}
