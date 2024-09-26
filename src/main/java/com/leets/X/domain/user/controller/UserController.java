package com.leets.X.domain.user.controller;

import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.leets.X.domain.user.controller.ResponseMessage.SUCCESS_SAVE;

// 스웨거에서 controller 단위 설명 추가
@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/")
    @Operation(summary = "테스트용 API")// 스웨거에서 API 별로 설명을 넣기 위해 사용하는 어노테이션
    public String index() {
        throw new UserNotFoundException();
    }

    // API 응답 예시를 위한 테스트 API
    @GetMapping("/test")
    @Operation(summary = "테스트용 API2")
    public ResponseDto<Void> test() {
        return ResponseDto.response(SUCCESS_SAVE.getCode(), SUCCESS_SAVE.getMessage());
    }

}
