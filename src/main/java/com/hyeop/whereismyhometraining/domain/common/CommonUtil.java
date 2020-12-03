package com.hyeop.whereismyhometraining.domain.common;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.AccountRepository;
import javassist.Loader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class CommonUtil {

    @Autowired
    private AccountRepository accountRepository;

    public String checkAccountShpaeImgPath(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String accountImgPath = "/img/shape/";
        if(!authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))){
            log.info("사람이");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));

            String upper = account.getUpperLevel() >= 30 ? "high"
                    : account.getUpperLevel() >= 0 ? "mid"
                    : "low";
            String core = account.getCoreLevel() >= 30 ? "high"
                    : account.getCoreLevel() >= 0 ? "mid"
                    : "low";
            String lower = account.getLowerLevel() >= 30 ? "high"
                    : account.getLowerLevel() >= 0 ? "mid"
                    : "low";
            return accountImgPath + upper + core + lower + ".png";
        }
        return accountImgPath + "midmidmid.png";
    }

    public String checkAccountShapeMessage(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String message = "";
        if(!authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("sdf"));
            Integer upperLevel = account.getUpperLevel();
            Integer lowerLevel = account.getLowerLevel();
            Integer coreLevel = account.getCoreLevel();
            Integer min = Math.min(upperLevel, Math.min(lowerLevel, coreLevel));
            message += "회원님의 체형을 보니... \n";
            if(upperLevel.equals(lowerLevel) && lowerLevel.equals(coreLevel)){
                message += "신체 밸런스가 잘 맞아요!";
            } else {
                if(min.equals(upperLevel)){
                    message += "[상체]";
                }
                if(min.equals(lowerLevel)){
                    message += "[하체]";
                }
                if(min.equals(coreLevel)){
                    message += "[복근]";
                }
                message += "를(을) 더 운동하시는게 어떨까요?";
            }
        }
        return message;
    }

}
