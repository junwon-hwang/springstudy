package com.study.springstudy.core.chap02;

public class Hotel {

    /*
     * @solution
     * - 먼저 DIP를 해결하기 위해 구체적인 객체 대신
     * 추상적인 역할에 의존하게 코드를 개선
     *
     * @problem - 추상화를 했지만 여전히 의존 객체를 바꾸려면
     *            코드를 직접 변경해야 한다.
     */

    // 레스토랑
    // (유연한 객체 생성 가능)
    private Restaurant restaurant = new WesternRestaurant();
    // private Restaurant restaurant = new AsianRestaurant();

    // 헤드쉐프
    private Chef headChef = new JannChef();
    // private Chef headChef = new KimuraChef();

    // 호텔을 소개하는 기능
    public void inform(){
        System.out.printf("우리 호텔의 레스토랑은 %s입니다." +
                "그리고 헤드쉐프는 %s입니다.\n"
                ,restaurant.getClass().getSimpleName()
                ,headChef.getClass().getSimpleName());

        restaurant.order();

    }
}

