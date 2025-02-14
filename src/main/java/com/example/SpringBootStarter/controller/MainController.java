package com.example.SpringBootStarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    // @ResponseBody : 자바 객체를 json 기반의 HTTP Body 로 변환
    // @RestController 사용시 @ResponseBody 어노테이션이 자동으로 붙게 됨
    @PostMapping(value = "/post")
    @ResponseBody
    public String post(@RequestBody String requestBody) {
        return "POST 요청 성공! : " + requestBody;
    }

    // React.js
    @GetMapping("/api/hello")
    @ResponseBody
    public String test() {
        return "Hello, world!";
    }
}
