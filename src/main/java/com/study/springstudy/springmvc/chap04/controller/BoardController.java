package com.study.springstudy.springmvc.chap04.controller;


import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import com.study.springstudy.springmvc.chap05.Service.ReactionService;
import com.study.springstudy.springmvc.chap05.dto.reponse.ReactionDto;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    // shift + f6 필드한번에 변경 가능
    // private final BoardRepository repo;
    private final BoardService boardService;
    private final ReactionService reactionService;


    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String show(@ModelAttribute("s") Search page , Model model){

        // 1. 서비스에서 조회 요청 위임
        List<BoardListResponseDto> bList = boardService.findAll(page);

        // 2. 페이지 정보를 생성하여 JSP에게 전송
        PageMaker maker = new PageMaker(page, boardService.getCount(page));


        // 3. JSP파일에 해당 목록데이터롤 보냄
        model.addAttribute("bList",bList);
        model.addAttribute("maker",maker);
//        model.addAttribute("s",page);

        return "/board/list";
    }

    // 2. 글쓰기 양식 화면 열기 요청 (/board/write : GET)
    @GetMapping("/write")
    public String write(){
        return "/board/write";
    }

    // 3. 게시글 등록 요청 (/board/write : POST)
    // -> 목록조회 요청 리다이렉션
    @PostMapping("/write")
    public String write(BoardWriteRequestDto dto, HttpSession session){
        System.out.println("dto = " + dto);
        boardService.save(dto,session);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(@RequestParam int bno){
        boardService.delete(bno);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 (/board/detail : GET)
    //  비회원 조회수 상승  X
    //  회원 자기가 쓴글은 조회수 상승 O
    //  회원 타인의 글 조회수 확인시 상승 O => 1시간에 한번만 가능
    @GetMapping("/detail")
    public String detail(int bno, Model model, HttpServletRequest request, HttpServletResponse response){


        model.addAttribute("bbb", boardService.findOne(bno,request,response));


        // 요청 헤더를 파싱하여 이전 페이지의 주소를 얻어냄
        String ref = request.getHeader("Referer");
        model.addAttribute("ref",ref);

        return "/board/detail";
    }

    // 좋아요 요청 비동기 처리
    @GetMapping("/like")
    @ResponseBody
    public ResponseEntity<?> like(long bno, HttpSession session){
        // 계정 조작 가능성 있으니 DB에서 로그인된 계정을 가져옴

        //로그인 검증
        if(!LoginUtil.isLoggedIn(session)){
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");
        }

        log.info("like async function");

        String account = LoginUtil.getLoggedInUserAccount(session);
        ReactionDto dto = reactionService.like(bno, account);// 좋아요 요청 처리
        return ResponseEntity.ok().body(dto);
    }

    // 싫어요 요청 비동기 처리
    @GetMapping("/dislike")
    @ResponseBody
    public ResponseEntity<?> dislike(long bno, HttpSession session){
        // 계정 조작 가능성 있으니 DB에서 로그인된 계정을 가져옴


        //로그인 검증
        if(!LoginUtil.isLoggedIn(session)){
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");
        }


        log.info("dislike async function");

        String account = LoginUtil.getLoggedInUserAccount(session);
        ReactionDto dto = reactionService.dislike(bno, account);// 싫어요 요청 처리
        return ResponseEntity.ok().body(dto);
    };

}
