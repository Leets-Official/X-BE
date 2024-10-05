package com.leets.X.global.auth.jwt;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
