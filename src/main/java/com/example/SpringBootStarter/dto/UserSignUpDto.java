package com.example.SpringBootStarter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpDto {

    private String userId;
    private String password;
}
