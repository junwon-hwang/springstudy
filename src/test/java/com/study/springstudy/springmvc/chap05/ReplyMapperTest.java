package com.study.springstudy.springmvc.chap05;

import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired BoardMapper boardMapper;
    @Autowired ReplyMapper replyMapper;

//    @Test
//    @DisplayName("")
//    void bulkInsert() {
//        // 게시물 100개와 댓글 5000개를 랜덤으로 등록
//        for (int i = 1; i <=100; i++) {
//            Board board = Board.builder()
//                    .title("재밌는 글" + i )
//                    .content("응 개노잼이야~~" + i)
//                    .writer("아무무나" + i)
//                    .build();
//
//            boardMapper.save(board);
//
//        }
//
//        for (int i = 1; i <= 5000 ; i++) {
//            Reply reply = Reply.builder()
//                    .replyText("하하호호댓글"+i)
//                    .replyWriter("꾸러기"+i)
//                    .boardNo((long) (Math.random()*100+1))
//                    .build();
//
//            replyMapper.save(reply);
//        }
//
//    }


}