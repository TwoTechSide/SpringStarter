package com.example.SpringBootStarter.service;

import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 빈(Bean) : 스프링 컨테이너에 의해 관리되는 재사용 가능한 소프트웨어 컴포넌트
    // 빈 등록 : @Bean, @Component, @Controller, @Service, @Repository
    @Autowired
    private UserRepository userRepository;

    // DB 에 user 저장
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}