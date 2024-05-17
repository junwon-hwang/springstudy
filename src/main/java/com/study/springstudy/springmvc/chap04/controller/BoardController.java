package com.study.springstudy.springmvc.chap04.controller;


import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board/*")
public class BoardController {

    private BoardRepository repo;

    @Autowired
    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

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
    public String write(Board board){
        // System.out.println(board);
        repo.save(board);


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
