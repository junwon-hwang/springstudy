package com.study.springstudy.springmvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coffee/*")
public class CoffeeController {

    /**
     * @request-uri : /coffee/order
     * @forwarding-jsp : /WEB-INF/views/mvc/coffee-form.jsp
     */
    @RequestMapping("/order")
    public String order(){
        return "mvc/coffee-form";
    }

    // POST 요청만 받겠다.
    // 과거방식 @RequestMapping(value = "/result", method = RequestMethod.POST)
    // 현재방식 @PostMapping("/result")
    // GET 요청만 받겠다. @GetMapping("/result")
    @PostMapping("/result")
    public String result(@RequestParam String menu, int price, Model model){

        // 1. 주문 데이터 (menu,price) 파라미터로 읽어오기
        // 2. JSP에 보내서 랜더링 (JSP에 보내기 위해 class spring model추가)
        model.addAttribute("mmm",menu);
        model.addAttribute("ppp",price);

        return "mvc/coffee-result";
    }
}
