package com.hyeop.whereismyhometraining.entity.userCalories;

import com.hyeop.whereismyhometraining.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserCaloriesRepository extends JpaRepository<UserCalories, Long> {

    List<UserCalories> findAllByAccountAndCreateDateBetween(Account account, LocalDateTime start, LocalDateTime end);
}
