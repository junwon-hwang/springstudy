package com.study.springstudy.springmvc.chap05.api;


import com.study.springstudy.springmvc.chap05.Service.LoginResult;
import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.util.FileUtil;
import com.study.springstudy.springmvc.util.LoginUtil;
import com.study.springstudy.webservlet.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @Value("${file.upload.root-path}")
    private String rootPath;
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

        MultipartFile profileImage = dto.getProfileImage();
        String profilePath = null;

        if(!profileImage.isEmpty()){

            log.debug("attached profile image name {}", profileImage.getOriginalFilename());

            // 서버에 업로드 후 업로드 경로 반환
            FileUtil.uploadFile(rootPath, dto.getProfileImage());

        }

        boolean flag = memberService.join(dto,profilePath);
        return flag ? "redirect:/members/sign-in" : "redirect:members/sign-up";
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
    public String signIn(LoginDto dto, HttpServletRequest request, RedirectAttributes ra, HttpServletResponse response) {
        log.info("/members/sign-in POST : forwarding to sign-in.jsp");
        log.debug("parameter: {}", dto);

        // 세션 얻기
        HttpSession session = request.getSession();


        LoginResult result = memberService.authenticate(dto, session,response);

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

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request , HttpServletResponse response) {
        // 세션 구하기
        HttpSession session = request.getSession();

        // 자동 로그인 상태인지 확인
        if(LoginUtil.isAutoLogin(request)){
             // 쿠키를 제거하고, DB에도 자동로그인 관련데이터를 원래대로 해놓음
            memberService.autoLoginClear(request,response);
        }

        // 세션에서 로그인 기록 삭제
        session.removeAttribute("login");

        // 세션을 초기화 (reset)
        session.invalidate();

        // 홈으로 보내기
        return "redirect:/";
    }




}
