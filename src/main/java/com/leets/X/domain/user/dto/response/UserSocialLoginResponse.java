package com.leets.X.domain.user.dto.response;

import com.leets.X.domain.user.service.LoginStatus;
import com.leets.X.global.auth.jwt.JwtResponse;
import lombok.Builder;

@Builder
public record UserSocialLoginResponse(
        Long id,
        LoginStatus status,
        JwtResponse jwtToken
) {
}
