package com.study.springstudy.springmvc.chap03.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Getter @Setter @ToString
@AllArgsConstructor
public class RankDto {

    private int rank, cnt;

}
