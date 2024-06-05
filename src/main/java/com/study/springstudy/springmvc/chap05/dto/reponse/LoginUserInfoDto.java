package com.study.springstudy.springmvc.chap05.dto.reponse;


import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginUserInfoDto {

    // 클라이언트에 보낼 정보
    private String account;
    private String nickName;
    private String email;
    private String auth;
    private String profile;

    public LoginUserInfoDto(Member member){
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.nickName = member.getName();
        this.auth = member.getAuth().name();
        this.profile = member.getProfileImg();
    }

}
