package com.leets.X.domain.user.service;

import com.leets.X.domain.follow.domain.Follow;
import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.image.service.ImageService;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.dto.request.UserInitializeRequest;
import com.leets.X.domain.user.dto.request.UserUpdateRequest;
import com.leets.X.domain.user.dto.response.UserProfileResponse;
import com.leets.X.domain.user.dto.response.UserSocialLoginResponse;
import com.leets.X.domain.user.exception.DuplicateCustomIdException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.leets.X.domain.user.domain.enums.LoginStatus.LOGIN;
import static com.leets.X.domain.user.domain.enums.LoginStatus.REGISTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final ImageService imageService;
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
        valid(dto.customId());
        user.initProfile(dto);
    }

    /*
        * 프로필 수정
     */
    @Transactional
    public void updateProfile(UserUpdateRequest dto, MultipartFile image, String email) throws IOException {
        User user = find(email);
        Image savedImage = user.getImage();
       // 이미지가 없다면 새로 생성해서 저장
        if(savedImage== null){
            savedImage = imageService.save(image, user);
        } else if (image != null) {
            ImageDto imageDto = imageService.getImage(image);
            savedImage.update(imageDto);
        }
        // 기존 이미지가 있다면 ImageDto를 생성해서 기존 이미지를 업데이트
        user.update(dto, savedImage);
    }

    public UserProfileResponse getProfile(String customId, String email){
        User user = userRepository.findByCustomId(customId)
                .orElseThrow(UserNotFoundException::new);
        boolean isMyProfile = user.getEmail().equals(email);
        boolean isFollowing = checkFollowing(user, email);
        return UserProfileResponse.from(user, isMyProfile, isFollowing, getImage(user));
    }
//
//    @Transactional
//    public void delete(Long userId){
//        userRepository.deleteById(userId);
//    }

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

    // 팔로우 되어있는 유저라면 true
    private boolean checkFollowing(User target, String email){
        User source = find(email);
        List<Follow> followerList = target.getFollowerList();

        return followerList.stream()
                .anyMatch(follow -> follow.getFollower().getId().equals(source.getId()));
    }

    private ImageDto getImage(User user){
        if(user.getImage() != null){
            Image image = user.getImage();
            return ImageDto.of(image.getName(), image.getUrl());
        }
        return null;
    }

    private void valid(String customId) {
        if(userRepository.existsByCustomId(customId)) {
            throw new DuplicateCustomIdException();
        }
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

    public User findByCustomId(String customId){
        return userRepository.findByCustomId(customId)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean existUser(String email){
        return userRepository.existsByEmail(email);
    }

}
