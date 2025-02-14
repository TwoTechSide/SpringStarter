package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseBody
    public String signup(@RequestBody UserDto userDto) {
        userService.saveUser(userService.dtoToEntity(userDto));

        return "signup-done";
    }
}