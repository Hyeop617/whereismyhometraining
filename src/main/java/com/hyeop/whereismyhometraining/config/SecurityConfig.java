package com.hyeop.whereismyhometraining.config;

import com.hyeop.whereismyhometraining.advice.CustomAuthenticationEntryPoint;
import com.hyeop.whereismyhometraining.advice.GlobalExceptionHandler;
import com.hyeop.whereismyhometraining.config.oauth2.CustomOAuth2Provider;
import com.hyeop.whereismyhometraining.config.oauth2.CustomOAuth2SuccessHandler;
import com.hyeop.whereismyhometraining.domain.account.AccountService;
import com.hyeop.whereismyhometraining.entity.RedisUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.*;

@EnableWebSecurity
@PropertySource("classpath:application-oauth.yml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Environment env;

    private static String CLIENT_PROPERTY_KEY= "spring.security.oauth2.client.registration.";

    private static List<String> clients = Arrays.asList("google", "naver");

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CookieProvider cookieProvider;

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()                  // Token 방식 로그인 이용하기 때문에 disable 처리
                .formLogin().disable()                  // Token 방식 로그인 이용하기 때문에 disable 처리
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)         // Token 방식 로그인 이용하기 때문에 Session STATELESS 설정
                    .and()
                // 유저 권한 필터 처리 전에 JWT 토큰 검사하는 필터 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, cookieProvider, redisUtil), UsernamePasswordAuthenticationFilter.class)
                // 권한 없을 시 예외 처리 추가
                .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(globalExceptionHandler)
                    .and()
                // 권한 설정
                .authorizeRequests()
                    .antMatchers("/mycourse", "/course", "/workout").authenticated()  // training 하위 페이지는 권한 필요
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().permitAll()                                         // 그 외 페이지는 모두 허가
                    .and()
                .logout()                                       // 로그아웃 시 설정
                    .logoutSuccessUrl("/")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .and()
                .oauth2Login()
                    .successHandler(customOAuth2SuccessHandler);
//                    .userInfoEndpoint()
//                        .userService(customOAuth2UserService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    // OAuth 2.0 Filter
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        List<ClientRegistration> registrations = new ArrayList<>();
        String googleClientId = env.getProperty(CLIENT_PROPERTY_KEY + "google.client-id");
        String googleClientSecret = env.getProperty(CLIENT_PROPERTY_KEY + "google.client-secret");
        String googleScope = env.getProperty(CLIENT_PROPERTY_KEY + "google.scope");
        String[] split = googleScope.split(", ");
        Set<String> googleScopeSet = new HashSet<>(Arrays.asList(split));
        registrations.add(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .scope(googleScopeSet)
                .build());
        registrations.add(CustomOAuth2Provider.NAVER.getBuilder().build());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(){
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }


}
