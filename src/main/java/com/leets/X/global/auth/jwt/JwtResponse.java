package com.leets.X.global.auth.jwt;

import lombok.Builder;

@Builder
public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
