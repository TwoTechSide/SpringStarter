package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.api.KakaoApiLoginUtil;
import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final KakaoApiLoginUtil kakaoApiLoginUtil;

    // 카카오 callback
    @GetMapping("/oauth2/kakao/handler")
    public Map<String, Object> oauth2KakaoLogin(@RequestParam String code) {

        Map<String, Object> data = new HashMap<>();

        // 1. 카카오 API 토큰 발급
        log.info("code : " + code);
        Map<String, String> res = kakaoApiLoginUtil.getTokens(code);

        // 2. 토큰으로 유저 DB 확인
        UserDto userDto = kakaoApiLoginUtil.getProfile(res.get("access_token"));
        boolean isExistUser = userRepository.findByUserId(userDto.getUserId()).isPresent();

        // 3. {esExistUser, userDto} 전달
        data.put("isExistUser", isExistUser);
        data.put("userData", userDto);

        return data;
    }

    @PostMapping("/sign-up")
    public boolean signup(@RequestBody UserDto userDto) {

        User user = User.builder()
                .email(userDto.getEmail())
                .userId(userDto.getUserId())
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .build();

        userService.saveUser(user);

        return true;
    }

    @PostMapping("/sign-in")
    public Object signin(@RequestBody UserSignUpDto signUpDto) {
        String userId = signUpDto.getUserId();

        Optional<User> user = userRepository.findByUserId(signUpDto.getUserId());

        if (user.isPresent()) {
            if (passwordEncoder.matches(signUpDto.getPassword(), user.get().getPassword())) {
                return userService.entityToDto(user.get());
            }
        }
        return "Incorrect email or password, please enter it again.";
    }

    @GetMapping("/checkEmail")
    public boolean checkEmail(@RequestParam String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
