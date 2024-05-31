package com.study.springstudy.springmvc.Interceptor;

import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
public class BoardInterceptor implements HandlerInterceptor {

    // preHandle을 구현하여
    // 로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부할 것!
    // 거부하고 로그인 페이지로 리다이렉션할 것!
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!LoginUtil.isLoggedIn(request.getSession())) {

            String redirectUri = request.getRequestURI();

            log.info("origin: {}", redirectUri);
            response.sendRedirect("/members/sign-in?message=login-required&redirect=" + redirectUri);
            return false;
        }

        // 삭제요청이 들어오면 서버에서 한번더 관리자인지? 자기가쓴글인지 체크


        return true;
    }
}