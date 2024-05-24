package com.study.springstudy.springmvc.chap05.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/rest")
@Controller
public class RestController {


    // 서버사이드랜더링 (SSR)
    /*
        @GetMapping("/rest/hello")
    public String hello(){

        return "hello";
    }

     */


    //
    @GetMapping("/rest/hello")
    @ResponseBody // 서버가 클라이언트에게 순수한 데이터를 전달할 때 사용
    // REST API 다양해진 클라이언트를 대비하기 위해 사용함
    public String hello(){

        return "안녕안녕 메롱메롱";
    }

    @GetMapping("/hobby")
    @ResponseBody
    public List<String> hobby(){
        List<String> hobbys = List.of("태권도", "장기", "댄스");
        return hobbys;
    }


}
