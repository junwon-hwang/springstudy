package com.study.springstudy.springmvc.chap05.dto.request;


import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyPostDto {

    private String text; // 댓글 내용
    private String author; // 댓글 작성자
    private Long bno; // 원본 글 번호

}
