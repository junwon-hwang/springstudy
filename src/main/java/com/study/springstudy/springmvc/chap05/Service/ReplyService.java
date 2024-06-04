package com.study.springstudy.springmvc.chap05.Service;

import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap05.dto.reponse.ReplyDetailDto;
import com.study.springstudy.springmvc.chap05.dto.reponse.ReplyListDto;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyModifyDto;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ReplyService {

    private static final Logger log = LoggerFactory.getLogger(ReplyService.class);
    private final ReplyMapper replyMapper;



    // 댓글 목록 전체 조회
    public ReplyListDto getReplies(long boardNo, Page page){
        List<Reply> replies = replyMapper.findAll(boardNo,page);

        List<ReplyDetailDto> dtoList = replies.stream()
                .map(r -> new ReplyDetailDto(r))
                .collect(Collectors.toList());

        return ReplyListDto.builder()
                .replies(dtoList)
                .pageInfo(new PageMaker(page,replyMapper.count(boardNo)))
                .build();

    }

    // 댓글 입력
    public boolean register(ReplyPostDto dto){
        Reply reply = Reply.builder()
                        .replyText(dto.getText())
                        .replyWriter(dto.getAuthor())
                        .boardNo(dto.getBno())
                        .build();

        boolean flag = replyMapper.save(reply);
        if(flag) log.info("댓글 등록 성공!! - {}", dto);
        else log.warn("댓글 등록 실패");

        return flag;


    }

    // 댓글 수정
    public ReplyListDto modify(ReplyModifyDto dto){
        replyMapper.modify(dto.toEntity());

        return getReplies(dto.getBno(), new Page(1, 10));
    }

    // 내가쓴댓글이 아니면 수정 삭제 안되게
    // 로그인 안하면 댓글입력창 X , 링크로 로그인 페이지 연결

    @Transactional
    // 댓글 삭제
    public ReplyListDto remove(long rno, HttpSession session) {
        // 댓글 번호로 원본 글번호 찾기
        long bno = replyMapper.findBno(rno);

        boolean flag = replyMapper.delete(rno);
        return flag ? getReplies(bno, new Page(1, 10)) : null;

    }

}
