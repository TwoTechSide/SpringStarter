package com.example.SpringBootStarter.dto;

import lombok.Getter;

public class NaverDto {

    @Getter
    public static class OAuthToken {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private int expires_in;
        private String error;
        private String error_description;
    }
}
