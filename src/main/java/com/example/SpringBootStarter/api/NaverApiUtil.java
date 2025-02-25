package com.example.SpringBootStarter.api;

import com.example.SpringBootStarter.dto.NaverDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NaverApiUtil {

    @Value("${naver.client_id}")
    private String clientId;

    @Value("${naver.client_secret}")
    private String clientSecret;

    private Map<String, String> tokens;

    public Map<String, String> getTokens(String code) {

        ObjectMapper objectMapper = new ObjectMapper();
        NaverDto.OAuthToken oAuthToken = null;

        String reqUrl = "https://nid.naver.com/oauth2.0/token";
        RestTemplate rt = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString(reqUrl)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> response = rt.getForEntity(uri, String.class);

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), NaverDto.OAuthToken.class);
            log.info("oAuthToken : " + oAuthToken.getAccess_token());

            tokens = new HashMap<>();
            tokens.put("access_token", oAuthToken.getAccess_token());
            tokens.put("refresh_token", oAuthToken.getRefresh_token());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    public Map<String, String> getProfile(String access_token) {

        String reqUrl = "https://openapi.naver.com/v1/nid/me";
        RestTemplate rt = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userDataMap = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);

        try {
            HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.exchange(reqUrl, HttpMethod.POST, reqMsg, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody()).get("response");
            String userId = jsonNode.get("id").asText();
            String email = jsonNode.get("email").asText();
            String nickname = jsonNode.get("nickname").asText();
            String gender = (jsonNode.get("gender").asText().equals("M") ? "Male" : "Female");

            userDataMap.put("userId", userId);
            userDataMap.put("email", email);
            userDataMap.put("nickname", nickname);
            userDataMap.put("gender", gender);

            return userDataMap;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
