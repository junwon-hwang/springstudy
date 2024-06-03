package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailReponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.springstudy.springmvc.util.LoginUtil.*;

@Service
@AllArgsConstructor

public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;
    private final LoginDto dto;

    public List<BoardListResponseDto> findAll(Search page) {
        List<BoardFindAllDto> boardList = boardMapper.findAll(page);

        // 조회해온 게시물리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹

        List<BoardListResponseDto> bList = boardList.stream()
                .map(b -> new BoardListResponseDto(b))
                .collect(Collectors.toList());
        return bList;
    }

    public boolean save(BoardWriteRequestDto dto, HttpSession session) {

        Board b = dto.toEntity();
        // 계정명을 엔터티에 추가 - 세션에서 계정명 가져오기
        b.setAccount(getLoggedInUserAccount(session));

        return boardMapper.save(b);

    }

    public boolean delete(int bno) {
        return boardMapper.delete(bno);
    }

    public BoardDetailReponseDto findOne(int bno, HttpServletRequest request, HttpServletResponse response) {

        // 게시물 정보 조회
        Board view = boardMapper.findOne(bno);

        //  비회원 조회수 상승 , 본인 글이면 조회수 증가 방지

        HttpSession session = request.getSession();

        //  회원 자기가 쓴글은 조회수 상승 O
        //  회원 타인의 글 조회수 확인시 상승 O => 1시간에 한번만 가능

        if(!isLoggedIn(session)|| isMine(view.getAccount(), getLoggedInUserAccount(session))){
            return  new BoardDetailReponseDto(view);

        }
        if(shouldIncreaseViewCount(bno,request,response)) boardMapper.upViewCount(bno);
        return new BoardDetailReponseDto(view);
    }

    // 조회수 증가 여부를 판단
    /*
        - 만약 내가 처음 조회한 상대방의 게시물이면
          해당 게시물 번호로 쿠키를 생성 쿠키 안에는 생성 시간을 저장
          수명은 1시간으로 설정
        - 이후 게시물 조회시 반복해서 쿠키를 조회한 후 해당 쿠키가 존재할 시
          false를 리턴하여 조회 수 증가를 방지
        - 쿠키 생성 예시
          ex) Cookie(name = view_101, 2024-06-03 14:11:30)
     */
    private boolean shouldIncreaseViewCount(int bno, HttpServletRequest request, HttpServletResponse response) {

        // 쿠키 검사

        String cookieName  = "view_" + bno;
        Cookie viewCookie = WebUtils.getCookie(request, cookieName);

        // 이 게시물에 대한 쿠키가 존재 -> 아까 조회한 게시물
        if(viewCookie != null){
            return false;
        }
        // 쿠키 생성
        makeViewCookie(cookieName,response);
        return true;
    }

    // 조회수 쿠키를 생성하고 클라이언트에 전송하는 메서드
    private void makeViewCookie(String cookieName, HttpServletResponse response) {
        Cookie newCookie = new Cookie(cookieName, LocalDateTime.now().toString());
        newCookie.setPath("/"); // 쿠키 사용 범위 결정
        newCookie.setMaxAge(60*60);

        response.addCookie(newCookie);

    }

    public int getCount(Search search) {
        return boardMapper.count(search);
    }
}
