package com.study.springstudy.springmvc.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/spring/chap01/*")
public class BasicController {
    // 디테일한 요청
    // URL : /spring/chap01/hello
    @RequestMapping("/hello")
    // public 고정 , 메서드 리턴 타입은 무조건 String
    public String hello(){
        System.out.println("hello 요청이 들어옴!!");
        // /WEB-INF/views/hello.jsp
        return "hello";
    }

    // ======= 요청 파라미터 받기 (Query String) ======= //
    // URL에 붙어있거나 form태그에서 전송된 데이터

    // 1. HttpServletRequest
    @RequestMapping("/person")
    public String person(HttpServletRequest request){
        System.out.println("/person!!!");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        return "";
    }

    // 2. @RequestParam
    // /spring/chap01/major?stu=kim&major=business&grade=3
    @RequestMapping("/major")
    public String major(@RequestParam String stu, String major, int grade){
        System.out.println("stu = " + stu);
        System.out.println("major = " + major);
        System.out.println("grade = " + grade);
        return "";
    }
    
    // 3. 커맨드 객체 사용 (RequestDTO) 사용하기
    // ex) /spring/chap01/order?orderNum=22&goods=구두&amount=3&price=20000...
    // 새로운 클래스 생성 , 필드생성 , set/get반드시 생성
    @RequestMapping("/order")
    public String order(OrderDTO order){
        System.out.println("주문번호 = " + order.getOrderNum());
        System.out.println("주문상품 = " + order.getGoods());
        System.out.println("주문수량 = " + order.getAmount());
        System.out.println("주문가격 = " + order.getPrice());
        return "";
    }


}
