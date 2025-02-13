package com.example.SpringBootStarter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    private String name;
    private int age;
    private String gender;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}