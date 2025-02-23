package com.example.SpringBootStarter.api;

import com.example.SpringBootStarter.dto.KakaoDto;
import com.example.SpringBootStarter.dto.UserDto;
import com.example.SpringBootStarter.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class KakaoApiLoginUtil {

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    private Map<String, String> tokens;

    public Map<String, String> getTokens(String code) {

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoDto.OAuthToken oAuthToken = null;

        // 토큰 받기 요청 - developers.kakao.com - REST API 문서 확인
        String reqUrl = "https://kauth.kakao.com/oauth/authorize";
        RestTemplate rt = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_url", redirectUri);
        params.add("code", code);

        // Header + Body
        HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, reqMsg, String.class);

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoDto.OAuthToken.class);
            log.info("oAuthToken : " + oAuthToken.getAccess_token());

            tokens = new HashMap<>();
            tokens.put("access_token", oAuthToken.getAccess_token());
            tokens.put("refresh_token", oAuthToken.getRefresh_token());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    public UserDto getProfile(String access_token) {

        // 사용자 정보 요청
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        RestTemplate rt = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


        try {
            HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.exchange(reqUrl, HttpMethod.POST, reqMsg, String.class);

            // API -> 유저 아이디, 이메일
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            UserDto userDto = UserDto.builder()
                            .userId(jsonNode.get("id").asText())
                            .email(jsonNode.get("kakao_account").get("email").asText())
                            .build();

            return userDto;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
