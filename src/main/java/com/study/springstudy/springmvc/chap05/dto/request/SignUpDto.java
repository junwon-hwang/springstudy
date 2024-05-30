package com.study.springstudy.springmvc.chap05.dto.request;

import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpDto {

    @NotBlank
    @Size(min=4, max=14)
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min=2, max=6)
    private String name;

    @NotBlank
    @Email
    private String email;

    public Member toEntity() {
        // 빌더 패턴은 순서 상관없이 가능 , 생성자로 객체생성하면 정해진 순서대로 작성해야함
        return Member.builder()
                .account(this.account)
                .password(this.password)
                .email(this.email)
                .name(this.name)
                .build();
    }
}
