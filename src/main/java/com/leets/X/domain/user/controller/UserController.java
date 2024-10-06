package com.leets.X.domain.user.controller;

import com.leets.X.domain.user.domain.enums.LoginStatus;
import com.leets.X.domain.user.dto.request.UserSocialLoginRequest;
import com.leets.X.domain.user.dto.response.UserSocialLoginResponse;
import com.leets.X.domain.user.servie.UserService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.leets.X.domain.user.controller.ResponseMessage.LOGIN_SUCCESS;
import static com.leets.X.domain.user.controller.ResponseMessage.USER_SAVE_SUCCESS;

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

}
