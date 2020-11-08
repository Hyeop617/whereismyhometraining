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

}
