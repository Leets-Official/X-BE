package com.leets.X.global.auth.google;

import com.leets.X.global.auth.google.dto.GoogleTokenResponse;
import com.leets.X.global.auth.google.dto.GoogleUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AuthService {

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
    @Value("${google.user-info-uri}")
    private String userInfoUri;

    private final RestClient restClient = RestClient.create();

    public GoogleTokenResponse getGoogleAccessToken(String authCode) {
        // 디코딩된 상태로 보내야 요청이 정상적으로 감
        String decode = URLDecoder.decode(authCode, StandardCharsets.UTF_8);

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

    public GoogleUserInfoResponse getGoogleUserInfo(String accessToken) {
        return restClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(GoogleUserInfoResponse.class);
    }

}
