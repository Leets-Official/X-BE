package com.leets.X.global.auth.google;

import com.leets.X.global.auth.google.dto.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final RestClient restClient = RestClient.builder().build();
    @Value("${google.client-id}")
    private String clientId;
    @Value("${google.client-secret}")
    private String clientSecret;
    @Value("${google.redirect-uri}")
    private String redirectUri;
    @Value("${google.grant-type}")
    private String grantType;
    @Value("${google.token-uri}")
    private String tokenUri;


    public GoogleTokenResponse getGoogleAccessToken(String code) {

        // 디코딩된 상태로 보내야 요청이 정상적으로 감
        String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        // 요청 body를 application/x-www-form-urlencoded에 맞게 보내기 위해 MultiValueMap 사용
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("code", decode);
        bodyParams.add("client_id", clientId);
        bodyParams.add("client_secret", clientSecret);
        bodyParams.add("redirect_uri", redirectUri);
        bodyParams.add("grant_type", grantType);

        return restClient.post()
                .uri(tokenUri)
                .body(bodyParams)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(GoogleTokenResponse.class);
    }

}
