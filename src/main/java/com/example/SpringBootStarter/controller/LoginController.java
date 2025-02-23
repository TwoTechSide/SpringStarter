package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.api.KakaoApUtil;
import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    private final KakaoApUtil kakaoApUtil;

    // 카카오 callback
    @GetMapping("/oauth2/kakao/handler")
    public User oauth2KakaoLogin(@RequestParam String code) {

        // 1. 카카오 API 토큰 발급
        log.info("code : " + code);
        Map<String, String> res = kakaoApUtil.getTokens(code);

        // 2. 토큰으로 유저 DB 확인
        Map<String, String> userDataMap = kakaoApUtil.getProfile(res.get("access_token"));
        Optional<User> optionalUser = userRepository.findByUserId(userDataMap.get("userId"));

        // 2-5. DB 에 등록되지 않은 경우 새로 저장
        User user;

        if (optionalUser.isEmpty()) {
            UserDto userDto = UserDto.builder()
                    .userId(userDataMap.get("userId"))
                    .email(userDataMap.get("email"))
                    .build();

            user = userService.saveUser(userService.dtoToEntity(userDto));
        } else {
            user = optionalUser.get();
        }

        return user;
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
