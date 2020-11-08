package com.hyeop.whereismyhometraining.domain.userCourse;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.CourseRepository;
import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetail;
import com.hyeop.whereismyhometraining.entity.courseDetail.CourseDetailRepository;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourse;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourseRepository;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseRequestDto;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseResponseDto;
import com.hyeop.whereismyhometraining.mapper.CourseDetailMapper;
import com.hyeop.whereismyhometraining.mapper.UserCourseMapper;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import com.hyeop.whereismyhometraining.response.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserCourseService {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;

    public Map<String, List<UserCourseResponseDto>> list(){
        Map<String, List<UserCourseResponseDto>> hm = new HashMap<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        List<UserCourseResponseDto> notFinished = userCourseRepository.findAllByAccount(account)
                                                                    .stream()
                                                                    .filter(uc -> uc.getIsFinish().equals(false))
                                                                    .map(UserCourseMapper.INSTANCE::toDto)
                                                                    .collect(Collectors.toList());
        List<UserCourseResponseDto> finished = userCourseRepository.findAllByAccount(account)
                                                                    .stream()
                                                                    .filter(uc -> uc.getIsFinish().equals(true))
                                                                    .map(UserCourseMapper.INSTANCE::toDto)
                                                                    .collect(Collectors.toList());
        hm.put("finished", finished);
        hm.put("notFinished", notFinished);

        return hm;
    }


    public ResponseEntity addUserCourse(UserCourseRequestDto dto) {
        //TODO :: Exception 수정
        HashMap<String, ResponseEntity> hm = new HashMap<>();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new RuntimeException("코스 없음"));
        hm.put("result", new ResponseEntity<>(HttpStatus.UNAUTHORIZED));                // 성별 다를시에.
        if(account.getGender().equals(course.getGender()) || course.getGender().equals("A")) {
            userCourseRepository.findByCourseAndAccount(course, account)
                    .ifPresentOrElse(
                            userCourse -> hm.put("result", new ResponseEntity<>(HttpStatus.CONFLICT)),
                            () -> {
                                userCourseRepository.save(UserCourse.builder()
                                        .account(account)
                                        .course(course)
                                        .day(dto.getDay())
                                        .workoutOrder(dto.getWorkoutOrder())
                                        .isFinish(false)
                                        .build());
                                hm.put("result", new ResponseEntity<>(HttpStatus.OK));
                            }
                    );
        }



        return hm.get("result");

    }

    public CourseDetailResponseDto view(Long id) {
        // TODO :: Exception 수정
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails);
        Account account = accountRepository.findByUsername(userDetails.getUsername())
                                           .orElseThrow(() -> new RuntimeException("sdf"));
        Course course = courseRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("코스 없음"));
        UserCourse userCourse = userCourseRepository.findByCourseAndAccount(course, account)
                                                    .orElseThrow(() -> new RuntimeException("없어 그런거"));
        CourseDetail courseDetail = courseDetailRepository.findByCourseAndDayAndWorkoutOrder(course, userCourse.getDay(), userCourse.getWorkoutOrder())
                                                          .orElseThrow(() -> new RuntimeException("없어"));
        CourseDetailResponseDto responseDto = CourseDetailMapper.INSTANCE.toDto(courseDetail);
        responseDto.setIsFirst(responseDto.getWorkoutOrder().equals(1));
        Integer count = courseDetailRepository.countByCourseAndDay(course, responseDto.getDay());
        responseDto.setProgressDay(((userCourse.getWorkoutOrder() - 1) * 100) / count);
        // 나이에 따른 운동 수 수정
        if (account.getAge() > 50) {            // 60대는 60%
            responseDto.setWorkoutCount((int) (responseDto.getWorkoutCount() * 0.6));
        } else if (account.getAge() > 30) {     // 40, 50대는 80%
            responseDto.setWorkoutCount((int) (responseDto.getWorkoutCount() * 0.8));
        }

        return responseDto;

    }

    public HashMap<String, ResponseResult> nextWorkout(UserCourseRequestDto dto) {
        HashMap<String, ResponseResult> hm = new HashMap<>();
        log.info("dto is {}" , dto.toString());
        //TODO :: exception, userdetail 수정.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("sdf"));
        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new RuntimeException("그런 코스 없습니다."));
        UserCourse userCourse = userCourseRepository.findByCourseAndAccount(course, account)
                .orElseThrow(() -> new RuntimeException("없어 그런거"));
        Optional<CourseDetail> courseDetail = courseDetailRepository
                .findByCourseAndDayAndWorkoutOrder(course, dto.getDay(), dto.getWorkoutOrder());
        courseDetail.ifPresentOrElse(
                                    cd -> {
                                        CourseDetailResponseDto responseDto = CourseDetailMapper.INSTANCE.toDto(cd);
                                        Integer count = courseDetailRepository.countByCourseAndDay(course, responseDto.getDay());
                                        responseDto.setProgressDay(((responseDto.getWorkoutOrder() - 1) * 100) / count);
                                        // 나이에 따른 운동 수 수정
                                        if (account.getAge() > 50) {            // 60대는 60%
                                            responseDto.setWorkoutCount((int) (responseDto.getWorkoutCount() * 0.6));
                                        } else if (account.getAge() > 30) {     // 40, 50대는 80%
                                            responseDto.setWorkoutCount((int) (responseDto.getWorkoutCount() * 0.8));
                                        }
                                        hm.put("result",responseService.getResult(responseDto));
                                        userCourse.changeOrder(userCourse.getWorkoutOrder()+1);
                                        userCourseRepository.save(userCourse);
                                    },() -> {
                                        if(dto.getDay().equals(course.getPeriod())){
                                            shapeCalc(account, course);
                                            hm.put("result", responseService.getResult("totalFinish"));
                                            userCourse.changeDay(userCourse.getDay() + 1);
                                            userCourse.changeFinish(true);
                                            userCourseRepository.save(userCourse);
                                        } else {
                                            hm.put("result", responseService.getResult("todayFinish"));
                                            userCourse.changeOrder(1);
                                            userCourse.changeDay(userCourse.getDay() + 1);
                                            userCourseRepository.save(userCourse);
                                        }
                                    }
                    );
        return hm;

    }

    public void shapeCalc(Account account, Course course){
        Integer courseLevel = course.getLevel();
        String courseType = course.getType();
        if(courseType.equals("upper") || courseType.equals("all")){
            account.changeUpperLevel(courseLevel);
        }
        if(courseType.equals("core") || courseType.equals("all")){
            account.changeCoreLevel(courseLevel);
        }
        if(courseType.equals("lower") || courseType.equals("all")){
            account.changeLowerLevel(courseLevel);
        }
        accountRepository.save(account);
    }
}
