package com.example.SpringBootStarter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder    // package-private 생성자 자동 생성
@Getter     // 접근자 자동 생성
@Entity     // build.gradle 에 jpa 를 추가해야 사용 가능, JPA = Java Persistence API
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 생성
@AllArgsConstructor                                 // 전체 생성자 생성
@EntityListeners(AuditingEntityListener.class)      // 엔티티 변화를 감지하고 테이블의 데이터를 조작하는 일
public class User {

    // @Id : 식별자 필드, @GenerateValue : 기본 키 생성, @Column : 엔티티 필드를 테이블의 칼럼에 매핑
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String nickname;
    private String password;
    private int age;
    private String gender;

    @Column(unique = true)
    private String email;

    @CreatedDate                                    // @EntityListeners 어노테이션의 AuditingEntityListener.class 으로 구현되는 어노테이션
    @Column(updatable = false, nullable = false)    // insertable = false : insert 시점 / updatable = false : update 시점
    private LocalDateTime createdAt;

    @LastModifiedDate                               // @EntityListeners 어노테이션의 AuditingEntityListener.class 으로 구현되는 어노테이션
    private LocalDateTime updatedAt;
}