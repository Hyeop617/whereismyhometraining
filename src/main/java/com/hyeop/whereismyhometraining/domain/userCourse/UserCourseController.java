package com.hyeop.whereismyhometraining.domain.userCourse;

import com.hyeop.whereismyhometraining.domain.common.CommonUtil;
import com.hyeop.whereismyhometraining.domain.courseDetail.CourseDetailFacade;
import com.hyeop.whereismyhometraining.entity.courseDetail.dto.CourseDetailResponseDto;
import com.hyeop.whereismyhometraining.entity.userCourse.dto.UserCourseRequestDto;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/mycourse")
public class UserCourseController {

    @Autowired
    private UserCourseFacade userCourseFacade;

    @Autowired
    private CommonUtil commonUtil;

    @GetMapping("")
    public String list(Model model){
        model.addAttribute("list",userCourseFacade.list().get("notFinished"));
        model.addAttribute("finished",userCourseFacade.list().get("finished"));
        return "/mycourse/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        CourseDetailResponseDto view = userCourseFacade.view(id);
        model.addAttribute("shapeImgPath", commonUtil.checkAccountShpaeImgPath());
        model.addAttribute("courseDetail", view);
        return "/mycourse/view";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity addUserCourse(@RequestBody UserCourseRequestDto dto) {
        return userCourseFacade.addUserCourse(dto);
    }

    @PostMapping("/nextWorkout")
    @ResponseBody
    public ResponseResult nextWorkout(@RequestBody UserCourseRequestDto dto){
        Map<String, ResponseResult> resultHashMap = userCourseFacade.nextWorkout(dto);
        return resultHashMap.get("result");
    }
}
