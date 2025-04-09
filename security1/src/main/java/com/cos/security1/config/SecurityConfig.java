package com.cos.security1.config;

import org.apache.coyote.Adapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 필터에 등록 됨
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").authenticated() // 인증된 사용자만 접근 가능
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 권한이 MANAGER 또는 ADMIN만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN인 사용자만 접근 가능
                        .anyRequest().permitAll() // 나머지 요청은 누구나 접근 가능
                )
                .formLogin(form -> form
                        .loginPage("/loginForm") // 사용자 작성 로그인 페이지
                        .loginProcessingUrl("/login") // 로그인 처리 url
                        .defaultSuccessUrl("/") // 로그인 성공 후 이동 url
                );


        return http.build();
    }
}
