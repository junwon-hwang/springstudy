package com.study.springstudy.springmvc.chap04.common;

import lombok.*;
import org.checkerframework.checker.units.qual.N;

@Getter @Setter @ToString
@EqualsAndHashCode

public class Search extends Page {

    // 검색어와 검색조건
    private String keyword, type;

    public Search(){
        this.keyword = "";
    }

}
