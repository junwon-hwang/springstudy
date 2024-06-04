package com.study.springstudy.springmvc.chap05.dto.reponse;

import com.study.springstudy.springmvc.chap04.common.PageMaker;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

/*
    {
        "replies":[
            {},{},{}
        ]
    }

 */

public class ReplyListDto {

    /*
        [
          {},{},{}
        ]
     */

    private LoginUserInfoDto loginUser;
    private PageMaker pageInfo;
    private List<ReplyDetailDto> replies;


}
