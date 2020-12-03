package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.CourseRepository;
import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import com.hyeop.whereismyhometraining.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final AccountRepository accountRepository;

    public HashMap<String, List<CourseResponseDto>> list() {
        List<Course> upper = courseRepository.findAllByType("upper");
        List<Course> lower = courseRepository.findAllByType("lower");
        List<Course> core = courseRepository.findAllByType("core");
        List<Course> aerobic = courseRepository.findAllByType("aerobic");
        HashMap<String, List<CourseResponseDto>> list = new HashMap<>();
        list.put("upperList", upper.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        list.put("lowerList", lower.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        list.put("coreList", core.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        list.put("aerobicList", aerobic.stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList()));
        return list;
    }

    public List<CourseResponseDto> listAll(){
        return courseRepository.findAll().stream().map(CourseMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public CourseResponseDto getRecommended() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        Integer weight = account.getWeight();
        Integer height = account.getHeight();
        Double bmi = weight / (height * height / 10000d);
        Integer coreLevel = account.getCoreLevel();
        Integer upperLevel = account.getUpperLevel();
        Integer lowerLevel = account.getLowerLevel();
        Integer min = Math.min(Math.min(coreLevel, upperLevel), lowerLevel);


        // 사용자 레벨과 각 부위별 레벨 ||로 계산해서 추천 코스 뿌림.
        Course course;
        if(bmi > 25){
            // 유산소 추천
                course = account.getLevel() > 2 ? courseRepository.findByLevelAndGenderAndType(2, "A", "aerobic").get()
                                                : courseRepository.findByLevelAndType(1, "aerobic").get();
        } else if (bmi > 23){
            if(min > 0){            // 무산소 추천
                course = checkCourse(account);
            } else {                // 유산소 추천
                course = account.getLevel() > 2 ? courseRepository.findByLevelAndGenderAndType(2, "A", "aerobic").get()
                                                : courseRepository.findByLevelAndType(1, "aerobic").get();
            }
        } else {
            course = checkCourse(account);
        }
        return CourseMapper.INSTANCE.toDto(course);

    }

    public Course checkCourse(Account account){
        Integer coreLevel = account.getCoreLevel();
        Integer upperLevel = account.getUpperLevel();
        Integer lowerLevel = account.getLowerLevel();
        Integer min = Math.min(Math.min(coreLevel, upperLevel), lowerLevel);

        if(min > 10 && account.getLevel() > 3) {
            return courseRepository.findByLevelAndGenderAndType(3, account.getGender(), "all").get();
        }else {
            if(min.equals(coreLevel)){
                return account.getLevel() > 2 ? courseRepository.findByLevelAndType(2, "core").get()
                                              : courseRepository.findByLevelAndType(1, "core").get();
            } else if (min.equals(upperLevel)){
                return account.getLevel() > 2 ? courseRepository.findByLevelAndType(2, "upper").get()
                                              : courseRepository.findByLevelAndType(1, "upper").get();
            } else {
                if(account.getGender().equals("M")){
                    return account.getLevel() > 2 ? courseRepository.findByLevelAndGenderAndType(2, "M", "all").get()
                                                  : courseRepository.findByLevelAndType(1, "lower").get();
                } else {
                    return account.getLevel() > 2 ? courseRepository.findByLevelAndGenderAndType(2,"F", "lower").get()
                                                  : courseRepository.findByLevelAndType(1, "lower").get();
                }
            }
        }
    }
}
