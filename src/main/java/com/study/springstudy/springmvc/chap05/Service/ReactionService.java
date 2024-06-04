package com.study.springstudy.springmvc.chap05.Service;

import com.study.springstudy.springmvc.chap05.dto.reponse.ReactionDto;
import com.study.springstudy.springmvc.chap05.entity.Reaction;
import com.study.springstudy.springmvc.chap05.entity.ReactionType;
import com.study.springstudy.springmvc.chap05.mapper.ReactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.study.springstudy.springmvc.chap05.entity.ReactionType.*;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionMapper reactionMapper;


    // 공통 리액션 DB처리 메서드
    private Reaction handleReaction(long boardNo, String account, ReactionType newReactionType){
        // <시나리오>
        // 1. 처음 리액션을 한다? -> 좋아요든 싫어요든 INSERT 쿼리
        // 2. 기존 리액션을 취소한다? -> 기존 데이터 DELETE 쿼리
        // 3. 기존 리액션을 변경한다? -> 기존 리액션을 DELETE 후 새로운 리액션을 INSERT 쿼리


        // 1. 현재 게시물에 특정 사용자가 리액션을 했는지 확인
        Reaction existingReaction = reactionMapper.findOne(boardNo, account);

        // 새 라이크 객체
        Reaction newReaction = Reaction.builder()
                .account(account)
                .boardNo(boardNo)
                .reactionType(newReactionType)
                .build();

        if(existingReaction!=null){
            // 처음 리액션이 아닌경우
            if(existingReaction.getReactionType() == newReactionType){
                // 동일한 리액션이기 때문에 취소 ( 좋아요 + 좋아요 )
                reactionMapper.delete(boardNo,account);
            }else{
                // 리액션 변경 ( 좋아요 + 취소 )
                reactionMapper.delete(boardNo,account); // 기존 리액션 DISLIKE 취소
                reactionMapper.save(newReaction); // 새 리액션 LIKE 생성
            }
        }else{
            // existingReaction === null 처음 리액션을 한경우
            // 새 리액션 LIKE 생성
            reactionMapper.save(newReaction);
        }

        // 리액션 한 후 재조회를 통해 DB데이터 상태를 체크
        return reactionMapper.findOne(boardNo,account);

    }

    private ReactionDto getReactionDto(long boardNo, Reaction reaction) {
        String reactionType = null;

        if(reaction != null){
            // 좋아요를 누른 상태
            reactionType = reaction.getReactionType().toString();
        }

        return ReactionDto.builder()
                .likeCount(reactionMapper.countLikes(boardNo))
                .disLikeCount(reactionMapper.countDisLikes(boardNo))
                .userReaction(reactionType)
                .build();
    }

    // 좋아요 중간처리
    public ReactionDto like(long boardNo, String account){
        Reaction reaction = handleReaction(boardNo, account, LIKE);
        return getReactionDto(boardNo, reaction);
    }


    // 싫어요 중간처리
    public ReactionDto dislike(long boardNo, String account) {
        Reaction reaction = handleReaction(boardNo, account, DISLIKE);
        return getReactionDto(boardNo, reaction);
    }
}
