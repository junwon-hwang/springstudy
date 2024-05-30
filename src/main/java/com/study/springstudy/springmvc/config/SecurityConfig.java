package com.study.springstudy.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Scanner;

@EnableWebSecurity // 시큐리티 설정 파일
public class SecurityConfig {

    // 시큐리티 기본 설정 (인증 인가 처리, 초기 로그인화면 없애기)

    @Bean //@Component (@Controller, @Service, @Repository, @Mapper 우리가 만든 클래스에 붙임)
    // @Bean 내가 만들지 않은 클래스에 붙임
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf().disable() // csrf 토큰공격
                // 모든 요청에 대해 인증하지 않겠다.
                .authorizeRequests().antMatchers("/**").permitAll();


        return http.build();
    }

    // 비밀번호를 인코딩하는 객체를 스프링 컨테이너에 등록
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }



    /*
        // ex
        @Bean
        public Scanner scanner(){
            return new Scanner(System.in);
        }
    */

}
