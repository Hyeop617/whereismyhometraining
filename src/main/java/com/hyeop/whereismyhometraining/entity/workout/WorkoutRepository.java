package com.hyeop.whereismyhometraining.entity.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

//    @Query("select w from Workout w where w.type = ?1 or w.type = 'all'")
    List<Workout> findAllByType(String type);
}
