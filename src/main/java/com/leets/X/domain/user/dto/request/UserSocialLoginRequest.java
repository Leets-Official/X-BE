package com.leets.X.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserSocialLoginRequest(
        @NotBlank String requestCode
) {
}
