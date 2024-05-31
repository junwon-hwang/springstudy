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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

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
        b.setAccount(LoginUtil.getLoggedUserAccount(session));

        return boardMapper.save(b);

    }

    public boolean delete(int bno) {
        return boardMapper.delete(bno);
    }

    public BoardDetailReponseDto findOne(int bno) {
        Board view = boardMapper.findOne(bno);
        if(view != null) boardMapper.upViewCount(bno);

//        List<Reply> replies = replyMapper.findAll(bno);

        BoardDetailReponseDto responseDto = new BoardDetailReponseDto(view);
//        responseDto.setReplies(replies);

        return responseDto;
    }

    public int getCount(Search search) {
        return boardMapper.count(search);
    }
}
