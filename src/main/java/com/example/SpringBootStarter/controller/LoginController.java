package com.example.SpringBootStarter.controller;

import com.example.SpringBootStarter.api.KakaoApiLoginUtil;
import com.example.SpringBootStarter.dto.KakaoDto;
import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.dto.UserSignUpDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import com.example.SpringBootStarter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public KakaoDto.OAuthToken oauth2KakaoLogin(@RequestParam String code) {
        log.info("code : " + code);
        return kakaoApiLoginUtil.oauth2KakaoLogin(code);
    }

    @PostMapping("/sign-up")
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
    public boolean checkEmail(@RequestParam String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
