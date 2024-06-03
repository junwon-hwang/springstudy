package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class LoginDto {

    private String account;
    private String password;
    private boolean autoLogin; // 자동 로그인 체크 여부

}
