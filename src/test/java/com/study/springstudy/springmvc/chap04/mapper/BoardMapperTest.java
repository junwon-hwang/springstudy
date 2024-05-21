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
    @DisplayName("전체조회")
    void findAllTest() {
        //given

        //when
        List<Board> boardList = mapper.findAll();
        //then
        System.out.println("boardList = " + boardList);
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        //given
        int boardNo = 18;
        //when
        boolean delete = mapper.delete(18);
        //then
        System.out.println("delete = " + delete);

    }


    @Test
    @DisplayName("개별조회")
    void findOneTest() {
        //given
        int boardNo = 20;
        //when
        Board one = mapper.findOne(20);
        //then
        System.out.println("one = " + one);
    }

    @Test
    @DisplayName("뷰카운트")
    void viewCountTest() {
        //given
        int boardNo = 21;
        //when
        mapper.upViewCount(21);
        //then
    }

    @Test
    @DisplayName("저장")
    void saveTest() {
        //given
        BoardWriteRequestDto board = new BoardWriteRequestDto("하2","하2","하2");
        //when
        boolean save = mapper.save(board);
        //then
        System.out.println("save = " + save);
    }




}