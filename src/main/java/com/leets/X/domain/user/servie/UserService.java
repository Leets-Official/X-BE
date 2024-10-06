package com.leets.X.domain.user.servie;

import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.dto.response.UserSocialLoginResponse;
import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.domain.user.repository.UserRepository;
import com.leets.X.global.auth.google.AuthService;
import com.leets.X.global.auth.google.dto.GoogleTokenResponse;
import com.leets.X.global.auth.google.dto.GoogleUserInfoResponse;
import com.leets.X.global.auth.jwt.JwtProvider;
import com.leets.X.global.auth.jwt.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.leets.X.domain.user.domain.enums.LoginStatus.LOGIN;
import static com.leets.X.domain.user.domain.enums.LoginStatus.REGISTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Transactional
    public UserSocialLoginResponse authenticate(String authCode) {
        GoogleTokenResponse token = authService.getGoogleAccessToken(authCode);
        GoogleUserInfoResponse userInfo = authService.getGoogleUserInfo(token.access_token());

        // 가입된 유저라면 로그인
        if (userRepository.existsByEmail(userInfo.email())){
            return loginUser(userInfo.email());
        }
        // 아니라면 회원가입
        return registerUser(userInfo);
    }

    private UserSocialLoginResponse loginUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserSocialLoginResponse(user.getId(), LOGIN, generateToken(email));
    }

    private UserSocialLoginResponse registerUser(GoogleUserInfoResponse userInfo) {
        User user = User.builder()
                .name(userInfo.name())
                .email(userInfo.email())
                .build();

        userRepository.save(user);

        return new UserSocialLoginResponse(user.getId(), REGISTER, generateToken(user.getEmail()));
    }

    private JwtResponse generateToken (String email){
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(email))
                .refreshToken(jwtProvider.generateRefreshToken())
                .build();
    }

}
