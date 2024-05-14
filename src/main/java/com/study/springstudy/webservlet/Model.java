package com.study.springstudy.webservlet;

import java.util.HashMap;
import java.util.Map;

// 역할 : JSP에게 보낼 데이터를 수송하는 역할
public class Model {

    private Map<String, Object> model = new HashMap<>();

    //데이터를 모델에 추가하는 매서드
    public void addAttribute(String key, Object value){
        model.put(key,value);
    }

    public Map<String, Object> getModelMap() {
        return model;
    }
}
