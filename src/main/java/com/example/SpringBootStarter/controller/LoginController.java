package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/kakao-api-key")
    @ResponseBody
    public String kakaoApiKey(@Value("${kakao.client_id}") String client_id, @Value("${kakao.redirect_uri}") String redirect_uri) {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
    }

    @GetMapping("/callback")
    public ResponseEntity callback(@RequestParam String code) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public boolean signup(@RequestBody UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return false;
        };

        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .build();

        userService.saveUser(user);

        return true;
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

    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
