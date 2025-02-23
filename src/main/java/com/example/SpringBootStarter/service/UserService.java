package com.example.SpringBootStarter.service;

import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final 또는 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 어노테이션
@Service
public class UserService {

    // @RequiredArgsConstructor 어노테이션으로 생성자 주입
    private final UserRepository userRepository;

    // DB 에 user 저장
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Dto -> Entity
    public User dtoToEntity(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .userId(userDto.getUserId())
                .nickname(userDto.getNickname())
                .password(userDto.getPassword())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .build();
    }

    // Entity -> Dto
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}