package com.example.SpringBootStarter;

import com.example.SpringBootStarter.entity.User;
import com.example.SpringBootStarter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootStarterApplicationTests {

	// userRepository 빈 등록
	@Autowired
	private UserRepository userRepository;

	@Test
	void createUserEntity() {
		User user = User.builder()
				.name("홍길동")
				.password("1234")
				.age(20)
				.gender("male")
				.email("hkd123@naver.com")
				.build();

		userRepository.save(user);
	}
}
