package com.example.SpringBootStarter.repository;

import com.example.SpringBootStarter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<EntityType, PK>
// JpaRepository 를 상속받아 기본적으로 제공되는 메서드들을 사용할 수 있음 (예 : save 로 DB 에 값 저장)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
