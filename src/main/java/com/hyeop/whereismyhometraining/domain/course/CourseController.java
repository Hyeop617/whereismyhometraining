package com.hyeop.whereismyhometraining.domain.course;

import com.hyeop.whereismyhometraining.domain.google.GoogleCalendarApiClient;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleApiResponse;
import com.hyeop.whereismyhometraining.domain.login.LoginFacade;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.course.Course;
import com.hyeop.whereismyhometraining.entity.course.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseFacade courseFacade;

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final GoogleCalendarApiClient googleCalendarApiClient;

    private final LoginFacade loginFacade;

    @GetMapping(value = {"", "/list"})
    public String list(Model model){
        Map<String, List<CourseResponseDto>> list = courseFacade.list();

        model.addAttribute("recommended", courseFacade.getRecommended());
        model.addAttribute("account", loginFacade.getAccountDetail());
        model.addAttribute("upperList", list.get("upperList"));
        model.addAttribute("lowerList", list.get("lowerList"));
        model.addAttribute("coreList", list.get("coreList"));
        model.addAttribute("aerobicList", list.get("aerobicList"));
        return "course/list";
    }
}
