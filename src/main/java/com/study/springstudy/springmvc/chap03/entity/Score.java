package com.study.springstudy.springmvc.chap03.entity;

import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;

// 역할: 데이터베이스의 테이블의 컬럼과 1:1로 매칭되는 필드를 가진 객체
public class Score {
    private long stuNum;
    private String stuName;
    private int kor;
    private int eng;
    private int math;
    private int total;
    private double average;
    private Grade Grade;


    public Score(ScorePostDto dto){
        this.stuName = dto.getName();
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        this.total = kor + eng + math;
        this.average = total/3.0;
        this.Grade = calcGrade();
    }

    private Grade calcGrade(){
        if(average>=90){
            return Grade.A;
        }else if(average>=80){
            return Grade.B;
        }else if(average>=70){
            return Grade.C;
        }else if(average>=60){
            return Grade.D;
        }else{
            return Grade.F;
        }
    }


    @Override
    public String toString() {
        return "Score{" +
                "stuNum=" + stuNum +
                ", stuName='" + stuName + '\'' +
                ", kor=" + kor +
                ", eng=" + eng +
                ", math=" + math +
                ", total=" + total +
                ", average=" + average +
                ", Grade=" + Grade +
                '}';
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public long getStuNum() {
        return stuNum;
    }

    public void setStuNum(long stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getKor() {
        return kor;
    }

    public void setKor(int kor) {
        this.kor = kor;
    }

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public com.study.springstudy.springmvc.chap03.entity.Grade getGrade() {
        return Grade;
    }

    public void setGrade(com.study.springstudy.springmvc.chap03.entity.Grade grade) {
        Grade = grade;
    }
}
