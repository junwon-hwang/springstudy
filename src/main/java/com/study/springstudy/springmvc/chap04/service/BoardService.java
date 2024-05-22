package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailReponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardMapper mapper;

    public List<BoardListResponseDto> findAll(Search page) {
        List<Board> boardList = mapper.findAll(page);

        // 조회해온 게시물리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹

        List<BoardListResponseDto> bList = boardList.stream()
                .map(b -> new BoardListResponseDto(b))
                .collect(Collectors.toList());
        return bList;
    }

    public boolean save(BoardWriteRequestDto dto) {

        Board b = dto.toEntity();
        return mapper.save(b);

    }

    public boolean delete(int bno) {
        return mapper.delete(bno);
    }

    public BoardDetailReponseDto findOne(int bno) {
        Board view = mapper.findOne(bno);
        if(view != null) mapper.upViewCount(bno);
        return new BoardDetailReponseDto(view);
    }

    public int getCount() {
        return mapper.count();
    }
}
