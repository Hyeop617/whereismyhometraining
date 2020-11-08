package com.hyeop.whereismyhometraining.domain.mail;

import com.hyeop.whereismyhometraining.entity.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    @Value("${mail.text}")
    private String MAIL_TEXT;

    public void sendVerificationMail(String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(FROM_ADDRESS);
        mail.setSubject("구해줘 홈트 - 이메일 인증");
        String code = String.format("%6d", (int) (Math.random() * 1000000 % 1000000));
        mail.setText(MAIL_TEXT + code);
        javaMailSender.send(mail);
        redisUtil.setDataExpire("verification-code"+email, code, 3 * 60 * 1000L);
    }


}
