package com.example.SpringBootStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing		// User 엔티티의 @CreatedDate 등을 사용하기 위해 선언
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)	// Spring Security 기본 로그인 화면 제외
public class SpringBootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStarterApplication.class, args);
	}

}