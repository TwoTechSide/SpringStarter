package com.example.SpringBootStarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("nickname", "유저");
        return "hello.html";
    }
}
