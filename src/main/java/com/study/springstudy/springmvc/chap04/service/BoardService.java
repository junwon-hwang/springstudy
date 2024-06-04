package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailReponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reaction;
import com.study.springstudy.springmvc.chap05.entity.viewLog;
import com.study.springstudy.springmvc.chap05.mapper.ReactionMapper;
import com.study.springstudy.springmvc.chap05.mapper.viewLogMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class BoardService {

    private final BoardMapper boardMapper;
    private final viewLogMapper viewLogMapper;
    private final ReactionMapper reactionMapper;

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

        String currentUserAccount = getLoggedInUserAccount(session); // 로그인 계정명
        int boardNo = view.getBoardNo();

        // 상세 조회시 초기 렌더링에 그려질 데이터
        BoardDetailReponseDto responseDto = new BoardDetailReponseDto(view);
        responseDto.setLikeCount(reactionMapper.countLikes(bno));
        responseDto.setDislikeCount(reactionMapper.countDisLikes(bno));

        Reaction reaction = reactionMapper.findOne(bno, currentUserAccount);

        String type = null;
        if(reaction!=null){
            type = reaction.getReactionType().toString();
        }

        responseDto.setUserReaction(type);

        if (!isLoggedIn(session) || isMine(view.getAccount(), currentUserAccount)) {
            return responseDto;

        }

        // 조회수가 올라가는 조건 처리 (쿠키버전)
        // if(shouldIncreaseViewCount(bno,request,response)) boardMapper.upViewCount(bno);
        // return new BoardDetailReponseDto(view);

        // 조회수가 올라가는 조건 처리 (데이터베이스 버전)

        // 1. 조회하는 글이 기록에 있는지 확인
        viewLog viewLog = viewLogMapper.findOne(currentUserAccount, boardNo);
        boolean shouldIncrease = false; // 조회수 올려도 되는지??
        viewLog viewLogEntity = viewLog.builder()
                .account(currentUserAccount)
                .boardNo(boardNo)
                .viewTime(LocalDateTime.now())
                .build();

        if (viewLog == null) {
            // 2. 이 게시물이 해당 회원에 의해서 처음 조회됨
            viewLogMapper.insertViewLog(viewLog.builder()
                    .account(currentUserAccount)
                    .boardNo(boardNo)
                    .viewTime(LocalDateTime.now())
                    .build()
            );
            shouldIncrease = true;
        } else{
            // 3. 조회기록이 있는 경우 - 1시간 이내 인지
            // 혹시 1시간이 지난 게시물 인지 확인
            LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
            if(viewLog.getViewTime().isBefore(oneHourAgo)){
               // 4. db에서 view_time 수정
                // 4. db에서 view_time 수정
                viewLogMapper.updateViewLog(viewLogEntity);
                shouldIncrease = true;

            }
        }
        if (shouldIncrease) {
            boardMapper.upViewCount(boardNo);
        }
        return responseDto;

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

//
//      게시물          게시물_해시태그                 해시태그
//
//         M              1                               N
//
//
//      tbl_board         tbl_hash_tag               tbl_board_tag
//  ==========================================================================
//
//      bno (글번호)         tag_no(해시태그번호)           id (PK)
//      title                       tag_title           bno (FK)
//      writer                                         tag_no(FK)
//          …
//
//
//              게시물
//              =========================
//
//              bno     title    writer
//              ——————————————————————-
//               1       aaa     kim
//               2       bbb     kim
//               3       ccc     park
//
//                  중간테이블
//              ==================
//           id     bno    tag_no
//           1       1           2
//           2       1           3
//           3       2           1
//           4       2           3
//           5       2           4
//
//
//
//                  해시태그
//          =========================
//
//              tag_no     tag_title
//          ——————————————————————
//               1           #칼퇴
//               2           #이대맛집
//               3           #날씨
//               4           #공부
//
//
//
//
//
//
//
//                  회원    게시물
//                   1          M
//
//                   FK (account)
//
//
