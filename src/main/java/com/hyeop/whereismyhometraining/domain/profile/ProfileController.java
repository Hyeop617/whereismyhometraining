package com.hyeop.whereismyhometraining.domain.profile;

import com.hyeop.whereismyhometraining.entity.account.dto.AccountCheckRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditRequestDto;
import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditResponseDto;
import com.hyeop.whereismyhometraining.entity.enums.Role;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileFacade profileFacade;

//    @Autowired
//    private HttpServletResponse response;

    @GetMapping("")
    public String view() throws IOException {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        log.info("auth is {} " , authentication.toString());
        if (authentication.contains(Role.SNS.getAuth()) || authentication.contains(Role.ADMIN.getAuth())) {
            return "redirect:/profile/edit";
        }
        return "profile/pw_check";
    }

    @GetMapping("/edit")
    public String editView(Model model) {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        ResponseResult responseResult = profileFacade.getAccount();
        ProfileEditResponseDto responseDto = (ProfileEditResponseDto) responseResult.getData();
        if(responseResult.getSuccess() || authentication.contains(Role.SNS.getAuth()) || authentication.contains(Role.ADMIN.getAuth())){
            model.addAttribute("account", responseDto);
            return "/profile/edit";
        }
        return "redirect:/profile";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity edit(@RequestBody ProfileEditRequestDto dto){
        return profileFacade.edit(dto);
    }

    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity check(@RequestBody String password){
        return profileFacade.check(password);
    }
}
