package com.example.SpringBootStarter.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder    // package-private 생성자 자동 생성
@Getter     // 접근자 자동 생성
@Entity     // build.gradle 에 jpa 를 추가해야 사용 가능, JPA = Java Persistence API
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 생성
@AllArgsConstructor                                 // 전체 생성자 생성
public class User {

    // @Id : 식별자 필드, @GenerateValue : 기본 키 생성, @Column : 엔티티 필드를 테이블의 칼럼에 매핑
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    private String name;
    private String password;
    private int age;
    private String gender;
    private String email;

    public User(int id, String name, int age, String gender, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }
}
