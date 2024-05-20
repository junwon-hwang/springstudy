package com.study.springstudy.springmvc.chap04.controller;


import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {


    private final BoardRepository repo;


    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String show(Model model){

        List<Board> boardList = repo.findAll();
        System.out.println(boardList);

        model.addAttribute("bList",boardList);

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
    public String write(BoardWriteRequestDto dto){
        // 1. 브라우저가 전달한 게시글 내용 읽기
        System.out.println("dto = " + dto);

        // 2. 해당 게시글을 데이터베이스에 저장하기 위해 엔터티 클래스로 변환
        Board b = dto.toEntity();
        // 3. 데이터베이스 저장 명령
        repo.save(b);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(int boardNo){
        repo.delete(boardNo);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(){
        return "/board/detail";
    }
}
