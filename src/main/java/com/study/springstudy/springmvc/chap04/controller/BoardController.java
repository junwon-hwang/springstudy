package com.study.springstudy.springmvc.chap04.controller;


import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailReponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {


    // private final BoardRepository repo;
    private final BoardService service;


    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String show(@ModelAttribute("s") Search page , Model model){

        // 1. 서비스에서 조회 요청 위임
        List<BoardListResponseDto> bList = service.findAll(page);

        // 2. 페이지 정보를 생성하여 JSP에게 전송
        PageMaker maker = new PageMaker(page, service.getCount(page));


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
        service.save(dto,session);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(@RequestParam int bno){
        service.delete(bno);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno, Model model, HttpServletRequest request){


        model.addAttribute("bbb",service.findOne(bno));


        // 요청 헤더를 파싱하여 이전 페이지의 주소를 얻어냄
        String ref = request.getHeader("Referer");
        model.addAttribute("ref",ref);

        return "/board/detail";
    }
}
