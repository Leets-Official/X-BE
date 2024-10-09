package com.leets.X.domain.user.controller;

import com.leets.X.domain.user.dto.request.UserInitializeRequest;
import com.leets.X.domain.user.dto.request.UserSocialLoginRequest;
import com.leets.X.domain.user.dto.response.UserSocialLoginResponse;
import com.leets.X.domain.user.service.LoginStatus;
import com.leets.X.domain.user.service.UserService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.leets.X.domain.user.controller.ResponseMessage.*;

// 스웨거에서 controller 단위 설명 추가
@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "구글 소셜 회원가입 및 로그인")
    public ResponseDto<UserSocialLoginResponse> socialLogin(@RequestBody @Valid UserSocialLoginRequest request) {
        UserSocialLoginResponse response = userService.authenticate(request.authCode());
        if(response.status() == LoginStatus.LOGIN){
            return ResponseDto.response(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), response);
        }
        return ResponseDto.response(USER_SAVE_SUCCESS.getCode(), USER_SAVE_SUCCESS.getMessage(), response);
    }

    /*
        * JWT 인증시 Authentication 객체를 만들 때 user의 email만 넣어서 만들었기 떄문에 @AuthenticationPrincipal로 인증 정보를 가져오면 email이 반환됨
        * 우리의 경우 소셜 로그인만 진행하기 때문에 이메일 중복이 없어 해당 방식도 문제 없지만, 토큰의 subject로 user_id와 email을 넣는게 나을 것 같음
        * 또한 인증된 사용자 정보를 객체, email로 가져오는 것보다 id로 가져오는 것이 인덱싱 방면에서 성능이 더 좋을 듯
     */
    @PatchMapping("/init")
    @Operation(summary = "최초 로그인시 정보 입력")
    public ResponseDto<String> initUserProfile(@RequestBody @Valid UserInitializeRequest request, @AuthenticationPrincipal @Parameter(hidden = true) String email) {// 스웨거에서 해당 정보 입력을 받지 않기 위해 hidden으로 설정
        userService.initProfile(request, email);
        return ResponseDto.response(INIT_PROFILE_SUCCESS.getCode(), INIT_PROFILE_SUCCESS.getMessage());
    }

}
