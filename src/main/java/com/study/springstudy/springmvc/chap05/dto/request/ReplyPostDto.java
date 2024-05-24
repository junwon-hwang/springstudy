package com.study.springstudy.springmvc.chap05.dto.request;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyPostDto {

    /*
        NotNull : null은  불가 빈문자 가능
        NotEmpty : null은 가능함 빈문자는 안됨
        NotBlank : null도 불가 빈문자 불가
     */

    @NotBlank
    @Size(min = 1, max = 300)
    private String text; // 댓글 내용
    @NotBlank
    @Size(min = 2, max = 8)
    private String author; // 댓글 작성자
    @NotNull
    private Long bno; // 원본 글 번호

}
