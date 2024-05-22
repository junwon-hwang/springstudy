package com.study.springstudy.springmvc.chap04.common;

// 페이지 화면 렌더링에 필요한 정보들을 계산

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@EqualsAndHashCode
public class PageMaker {

    // 한 화면에 페이지를 몇개씩 배치할 것인가?
    private static final int PAGE_COUNT = 10;

    // 페이지 시작번호와 끝번호
    private int begin, end;

    // 현재 페이지 정보
    private Page pageInfo;

    public PageMaker(Page page){
        this.pageInfo = page;
        makePageInfo();
    }


    // 페이지 생성에 필요한 데이터를 만드는 알고리즘
    private void makePageInfo(){

        // 1. end값을 계산
        /*
            지금 사용자가 7페이지를 보고있다면
            페이지 구간 : 1~10 구간

            지금 사용자가 24페이지를 보고있다면
            페이지 구간 : 21~30 구간

            // 5개씩 페이지를 배치하는 경우
            7page : 6~10
            24page : 21~25

            // 공식: (올림 (현재 사용자가 위치한 페이지넘버 / 한 화면에 보여줄 페이지 수)) * 한 화면에 보여줄 페이지 수

         */

        this.end = (int) (Math.ceil(pageInfo.getPageNo()/(double) PAGE_COUNT)*PAGE_COUNT);

        // 2. begin
        this.begin = this.end - PAGE_COUNT + 1;


    }

}
