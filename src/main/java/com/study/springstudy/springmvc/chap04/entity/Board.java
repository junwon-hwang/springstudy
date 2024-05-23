package com.study.springstudy.springmvc.chap04.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


// entity = DB테이블과 1:1 매칭하는 곳

/*
    CREATE TABLE tbl_board (
	    board_no INT(8) PRIMARY KEY auto_increment,
        title VARCHAR(200) NOT NULL,
        content TEXT,
        writer VARCHAR(100) NOT NULL,
        view_count INT(8) DEFAULT 0,
        reg_date_time DATETIME DEFAULT current_timestamp
    );
 */

// DB테이블 -> 1:1매칭 필드 -> repository(interface) -> 구현체(board repo) ->

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    private int boardNo; // 게시글번호
    private String title; // 글제목
    private String content; // 글내용
    private String writer; // 작성자명
    private int viewCount; // 조회수
    private LocalDateTime regDateTime; // 작성일시

    public Board(ResultSet rs) throws SQLException{

        this.boardNo = rs.getInt("board_no");
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.writer = rs.getString("writer");
        this.viewCount = rs.getInt("view_count");
        this.regDateTime = rs.getTimestamp("reg_date_time").toLocalDateTime();
    }
}
