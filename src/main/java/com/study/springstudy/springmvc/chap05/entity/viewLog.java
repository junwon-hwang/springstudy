package com.study.springstudy.springmvc.chap05.entity;


//    -- 조회수 기록 관리 테이블
//    CREATE TABLE view_Log(
//            id INT PRIMARY KEY auto_increment,
//            account VARCHAR(50),
//    board_no INT,
//    view_tiem DATETIME
//    );


import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class viewLog {


    private long id;
    private String account;
    private long boardNo;
    private LocalDateTime viewTime;

}
