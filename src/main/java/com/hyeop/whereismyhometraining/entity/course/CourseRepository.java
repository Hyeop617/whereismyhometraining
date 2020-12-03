package com.hyeop.whereismyhometraining.entity.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c where c.type = ?1 or c.type = 'all'")
    List<Course> findAllByType(String type);

    Optional<Course> findAllById(Long id);

    Optional<Course> findByLevelAndType(Integer level, String type);

    Optional<Course> findByLevelAndGenderAndType(Integer level, String gender, String type);

}
