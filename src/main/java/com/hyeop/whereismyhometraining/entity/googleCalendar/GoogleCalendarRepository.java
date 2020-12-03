package com.hyeop.whereismyhometraining.entity.googleCalendar;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleCalendarRepository extends JpaRepository<GoogleCalendar, Long> {

    List<GoogleCalendar> findAllByAccountAndCourse(Account account, Course course);

    List<GoogleCalendar> findAllByAccount(Account account);
}
