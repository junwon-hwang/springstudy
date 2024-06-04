package com.study.springstudy.springmvc.chap05.entity;

/*
    CREATE TABLE tbl_reply(
     reply_no INT(8) PRIMARY KEY auto_increment,
     reply_text VARCHAR(1000) NOT NULL,
     reply_writer VARCHAR(100) NOT NULL,
     reply_date DATETIME default current_timestamp,
     board_no INT(8),
     constraint fk_reply
     foreign key (board_no)
     references tbl_board(board_no)
     on delete cascade
    );
 */

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Setter는 필요에 의해서만 만들기
// 필드에 롬복 Setter 달아서 개별적 설정 가능
public class Reply {

    // DB와 1:1 매칭
    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;
    private String account;

}
