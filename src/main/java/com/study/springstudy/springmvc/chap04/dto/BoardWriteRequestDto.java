package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import lombok.*;

// dto의 필드명은 반드시 html form태그의 name속성과 일치해야함
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteRequestDto {

    private String writer;
    private String content;
    private String title;

    public Board toEntity() {
        Board b = new Board();
        b.setContent(this.content);
        b.setWriter(this.writer);
        b.setTitle(this.title);
        return b;
    }
}
