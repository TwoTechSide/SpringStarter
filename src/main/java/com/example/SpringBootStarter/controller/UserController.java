package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/sign-up")
    @ResponseBody
    public String signup(@RequestBody UserSignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        userService.saveUser(user);

        return "signup-done";
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public Object signin(@RequestBody UserSignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if (user.isPresent()) {
            if (passwordEncoder.matches(signUpDto.getPassword(), user.get().getPassword())) {
                return userService.entityToDto(user.get());
            }
        }
        return "Incorrect email or password, please enter it again.";
    }
}