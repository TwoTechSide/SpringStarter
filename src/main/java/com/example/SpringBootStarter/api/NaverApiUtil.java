package com.example.SpringBootStarter.api;

import com.example.SpringBootStarter.dto.NaverDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params);
        ResponseEntity<String> response = rt.exchange(reqUrl, HttpMethod.POST, reqMsg, String.class);

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
}
