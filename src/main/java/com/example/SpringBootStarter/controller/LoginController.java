package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // frontend/src/pages/login.js 로 REST API 키와 redirect url 전달
    @GetMapping("/kakao-api-key")
    @ResponseBody
    public String kakaoApiKey(@Value("${kakao.client_id}") String kakaoApiKey, @Value("${kakao.redirect_uri}") String kakaoRedirectUrl) {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakaoApiKey+"&redirect_uri="+kakaoRedirectUrl;
    }

    // 카카오 callback
    @GetMapping("/oauth2/code/kakao")
    @ResponseBody
    public String oauth2KakaoLogin(@RequestParam String code, @Value("${kakao.client_id}") String kakaoApiKey, @Value("${kakao.redirect_uri}") String kakaoRedirectUrl) {

        // 토큰 받기 요청 - developers.kakao.com - REST API 문서 확인
        String reqUrl = "https://kauth.kakao.com/oauth/authorize";
        RestTemplate rt = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_url", kakaoRedirectUrl);
        params.add("code", code);

        // Header + Body
        HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, reqMsg, String.class);

        return response.getBody();
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
