package com.study.springstudy.chap03;

import com.study.springstudy.core.chap03.Hotel;
import com.study.springstudy.core.chap03.config.HotelManager;
import org.junit.jupiter.api.Test;

class HotelDiTest {

    @Test
    void diTest(){
//        new Hotel(
//                new AsianRestaurant(
//                        new KimuraChef(),
//                        new SushiCourse()),
//                new KimuraChef());


        HotelManager manager = new HotelManager();
        Hotel hotel = manager.hotel();
        hotel.inform();


    }

}