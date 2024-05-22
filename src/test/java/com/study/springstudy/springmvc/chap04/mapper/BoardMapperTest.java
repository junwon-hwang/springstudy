package com.study.springstudy.springmvc.chap04.mapper;

import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

    @Test
    @DisplayName("")
    void insertTest(){
        for (int i = 1; i <=300 ; i++) {
            Board board = new Board();
            board.setTitle("테스트제목"+i);
            board.setWriter("글쓴이"+i);
            board.setContent("글내용"+i);

            mapper.save(board);
        }
    }


}