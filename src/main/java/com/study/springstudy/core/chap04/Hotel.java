package com.study.springstudy.core.chap04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // 호텔의 객체생성 제어권을 스프링에게 넘김
public class Hotel {

    // 레스토랑
    // @Autowired // 자동으로 객체를 주입
    // 필드에 final설정하여 객체 불변성을 보장해줘야함
    private final Restaurant restaurant ;

    // 헤드쉐프
    // @Autowired
    private final Chef headChef ;

    // 만약에 해당 클래스의 생성자가 단 한개뿐이라면 자동으로
    // @Autowired를 붙여준다
    @Autowired
    public Hotel(@Qualifier("western") Restaurant restaurant, Chef headChef) {
        this.restaurant = restaurant;
        this.headChef = headChef;
    }

    // 호텔을 소개하는 기능
    public void inform(){
        System.out.printf("우리 호텔의 레스토랑은 %s입니다." +
                "그리고 헤드쉐프는 %s입니다.\n"
                ,restaurant.getClass().getSimpleName()
                ,headChef.getClass().getSimpleName());

        restaurant.order();

    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

//    // @Autowired
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }

    public Chef getHeadChef() {
        return headChef;
    }
//    // @Autowired
//    public void setHeadChef(Chef headChef) {
//        this.headChef = headChef;
//    }
}

