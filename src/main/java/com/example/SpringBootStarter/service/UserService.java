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

    // Entity -> Dto
    public UserDto dtoToEntity(UserDto userDto) {
        return UserDto.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .build();
    }

    // Dto -> Entity
    public User entityToDto(User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .build();
    }
}