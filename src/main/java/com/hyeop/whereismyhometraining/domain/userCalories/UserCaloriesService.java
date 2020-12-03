package com.hyeop.whereismyhometraining.domain.userCalories;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.userCalories.UserCalories;
import com.hyeop.whereismyhometraining.entity.userCalories.UserCaloriesRepository;
import com.hyeop.whereismyhometraining.entity.userCalories.dto.UserCaloriesResponseDto;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserCaloriesService {

    @Autowired
    private UserCaloriesRepository userCaloriesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ResponseService responseService;

    public ResponseResult calc() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("userdetails is {}", userDetails);
        Optional<Account> account = accountRepository.findByUsername(userDetails.getUsername());
        Double upperCalories = 0d;
        Double lowerCalories = 0d;
        Double coreCalories = 0d;
        Double allCalories = 0d;
        Double totalCalories =0d;
        if(account.isPresent()){
            LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
            System.out.println(start);
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
            System.out.println(end);
            List<UserCalories> userCaloriesList = userCaloriesRepository.findAllByAccountAndCreateDateBetween(account.get(), start, end);

            for(UserCalories uc : userCaloriesList){
                switch (uc.getWorkoutType()) {
                    case "all":
                        allCalories += uc.getCalories();
                        break;
                    case "core":
                        coreCalories += uc.getCalories();
                        break;
                    case "upper":
                        upperCalories += uc.getCalories();
                        break;
                    case "lower":
                        lowerCalories += uc.getCalories();
                        break;
                }
                totalCalories += uc.getCalories();
            }
        }
        UserCaloriesResponseDto responseDto = UserCaloriesResponseDto
                .builder()
                .upperCalories(Math.round(upperCalories * 10)/ 10.0)
                .lowerCalories(Math.round(lowerCalories * 10)/ 10.0)
                .coreCalories(Math.round(coreCalories * 10)/ 10.0)
                .allCalories(Math.round(allCalories * 10)/ 10.0)
                .totalCalories(Math.round(totalCalories * 10)/ 10.0)
                .build();
        log.info("responseDto is {}", responseDto.toString());

        return responseService.getResult(responseDto);

    }
}
