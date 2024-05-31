package com.study.springstudy.springmvc.chap05.api;


import com.study.springstudy.springmvc.chap05.Service.LoginResult;
import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.webservlet.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final com.study.springstudy.springmvc.chap05.Service.MemberService memberService;

    // 회원 가입 양식 열기
    @GetMapping("/sign-up")
    public void signUp(){
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
        // return "members/sign-up";
    }

    // 회원 가입 요청 처리
    @PostMapping("/sign-up")
    public String signUp(@Validated SignUpDto dto){
        log.info("/members/sign-up POST");
        log.debug("parameter:{}",dto);

        boolean flag = memberService.join(dto);
        return flag ? "redirect:/memebers/sign-in" : "redirect:memebers/sign-up";
    }

    // 아이디,이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword){
        boolean flag = memberService.checkIdentifier(type, keyword);
        log.debug("{} 중복체크 결과? {}",type, flag);
        return ResponseEntity
                .ok()
                .body(flag);
    }


    // 로그인 양식 열기
    @GetMapping("/sign-in")
    public void signIn(){
        log.info("/members/sign-in GET : forwarding to sign-in.jsp");
        // return "members/sign-up";
    }

    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto, HttpServletRequest request, RedirectAttributes ra) {
        log.info("/members/sign-in POST : forwarding to sign-in.jsp");
        log.debug("parameter: {}", dto);

        // 세션 얻기
        HttpSession session = request.getSession();


        LoginResult result = memberService.authenticate(dto, session);

        // 로그인 검증 결과를 JSP에 보내기
        // Redirect시에 Redirect된 페이지에 데이터를 보낼 때는
        // Model 객체를 사용할 수 없음
        // 왜냐면 Model객체는 request객체를 사용하는데 해당 객체는
        // 한번의 요청이 끝나면 메모리에서 제거된다. 그러나 redirect는
        // 요청이 2번 발생하므로 다른 request객체를 jsp가 사용하게 됨
        // 결국 Redirect는 Model을 사용하여 Jsp로 보낼 수 없음
        // RedirectAttribute를 사용해야함

        ra.addFlashAttribute("result",result);

        if (result == LoginResult.SUCCESS) {
            return "redirect:/index"; // 로그인 성공시
        }
        return "redirect:/members/sign-in";
    }
}
