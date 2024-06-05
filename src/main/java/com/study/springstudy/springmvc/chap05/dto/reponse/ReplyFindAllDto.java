package com.study.springstudy.springmvc.chap05.dto.reponse;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyFindAllDto {

    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;
    private String account;
    private String profileImg;

}
