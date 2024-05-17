package com.study.springstudy.springmvc;

import lombok.*;



@Setter @Getter
// setter , getter를 수동으로 만들면 필드이름 변경시 둘다 수정해줘야함
// lombok 라이브러리를 이용하면 자동으로 수정됨

@ToString
@EqualsAndHashCode
// @NoArgsConstructor // 기본생성자
// @AllArgsConstructor // 모든필드 초기화 생성자 (final+나머지)
// @RequiredArgsConstructor // 필수 파라미터(final필드) 초기화 생성자

public class Student {

    private String name;
    private int age;
    private int grade;



}
