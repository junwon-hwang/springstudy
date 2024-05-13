package com.study.springstudy.chap03.config;

import com.study.springstudy.chap03.*;

// 객체 생성의 제어권을 모두 가지고 온 객체
public class HotelManager {

    // 쉐프 객체 생성
    public Chef chef1(){
        return new JannChef();
    }

    // 쉐프 객체 생성
    public Chef chef2(){
        return new KimuraChef();
    }

    // 요리 코스 객체 생성
    public Course course1(){
        return new FrenchCourse();
    }

    // 요리 코스 객체 생성
    public Course course2(){
        return new SushiCourse();
    }


    // 레스토랑 객체 생성
    public Restaurant restaurant1(){
        return new WesternRestaurant(chef1(),course1());
    }

    // 레스토랑 객체 생성
    public Restaurant restaurant2(){
        return new AsianRestaurant(chef2(),course2());
    }

    // 호텔 객체 생성
    // 호텔 객체만 수정하면 쉐프,레스토랑등 모든 객체 일괄 수정가능
    // 호텔매니저의 역할을 **스프링**이 해줌
    public Hotel hotel(){
        return new Hotel(restaurant1(),chef1());
    }

}
