package com.hyeop.whereismyhometraining.entity.userCourse;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.course.Course;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {

    Optional<UserCourse> findByCourseAndAccount(Course course, Account account);

    List<UserCourse> findAllByAccount(Account account);

    List<UserCourse> findAllByAccountAndIsFinish(Account account, Boolean isFinish);
}
