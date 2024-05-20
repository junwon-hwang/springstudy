package com.study.springstudy.springmvc.chap03.dto;

import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.Getter;

@Getter
public class ScoreListResponseDto {
    private final long stuNum; // 학번
    private String maskingName; // 첫글자 빼고 모두 별처리
    private double average; // 평균
    private String grade; // 등급

    public ScoreListResponseDto(Score s){
        this.stuNum = s.getStuNum();
        this.maskingName = makeMaskingName(s.getStuName());
        this.average = s.getAverage();
        this.grade = s.getGrade().toString();
    }

    private String makeMaskingName(String stuName) {
        char firstletter = stuName.charAt(0);
        String maskedName = "" + firstletter;
        for (int i = 0; i < stuName.length()-1 ; i++) {
            maskedName += "*";
        }
        return maskedName;
    }
}
