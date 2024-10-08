package com.leets.X.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserInitializeRequest(
        // 이 둘은 필수 입력이기 떄문에 NotBlank 제약
        @NotNull LocalDate birth, // 날짜의 경우 @NotNull이 적용 x
        @NotBlank String customId
) {
}
