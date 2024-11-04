package com.leets.X.domain.user.service;

import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.dto.request.UserInitializeRequest;
import com.leets.X.domain.user.dto.request.UserUpdateRequest;
import com.leets.X.domain.user.dto.response.UserProfileResponse;
import com.leets.X.domain.user.dto.response.UserSocialLoginResponse;
import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.domain.user.repository.UserRepository;
import com.leets.X.global.auth.google.AuthService;
import com.leets.X.global.auth.google.dto.GoogleTokenResponse;
import com.leets.X.global.auth.google.dto.GoogleUserInfoResponse;
import com.leets.X.global.auth.jwt.JwtProvider;
import com.leets.X.global.auth.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.leets.X.domain.user.service.LoginStatus.LOGIN;
import static com.leets.X.domain.user.service.LoginStatus.REGISTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    /*
        * 소셜 로그인
     */
    @Transactional
    public UserSocialLoginResponse authenticate(String authCode) {
        GoogleTokenResponse token = authService.getGoogleAccessToken(authCode);
        GoogleUserInfoResponse userInfo = authService.getGoogleUserInfo(token.access_token());

        String email = userInfo.email();

        // 가입된 유저라면 로그인
        if (existUser(email)){
            return loginUser(userInfo.email());
        }
        // 아니라면 회원가입
        return registerUser(userInfo);
    }

    /*
        * 회원가입 시 초기 정보 입력
     */
    @Transactional
    public void initProfile(UserInitializeRequest dto, String email){
        User user = find(email);

        user.initProfile(dto);
    }

    /*
        * 프로필 수정
     */
    @Transactional
    public void updateProfile(UserUpdateRequest dto, String email){
        User user = find(email);

        user.update(dto);
    }

    public UserProfileResponse getProfile(Long userId, String email){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        // 내 프로필이라면 true
        if(user.getEmail().equals(email)){
            return UserProfileResponse.from(user, true);
        }
        // 아니라면 false
        return UserProfileResponse.from(user, false);
    }

    private UserSocialLoginResponse loginUser(String email) {
        User user = find(email);

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

    /*
        * userRepository에서 사용자를 검색하는 메서드
        * 공통으로 사용되는 부분이 많기 때문에 별도로 분리
     */
    public User find(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User find(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean existUser(String email){
        return userRepository.existsByEmail(email);
    }

}
