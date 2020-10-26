package com.hyeop.whereismyhometraining.domain.userCourse;

import com.hyeop.whereismyhometraining.config.CookieProvider;
import com.hyeop.whereismyhometraining.config.JwtProvider;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import com.hyeop.whereismyhometraining.entity.userCourse.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private AccountRepository accountRepository;

    private HttpServletRequest req;

    @Autowired
    private CookieProvider cookieProvider;

    @Autowired
    private JwtProvider jwtProvider;

    public void add(){
//        Cookie accessCookie = cookieProvider.getCookie(req, "accessToken");
//        String accessToken = accessCookie.getValue();
//        jwtProvider.getUsername(accessToken);
//
//        UserCourse.builder().account()
//
//        userCourseRepository.save()
    }
}
