package com.leets.X.global.auth.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtProvider {

    @Value("${x.jwt.key}")
    private String jwtKey;
    private Key key;
    @Value("${x.jwt.access.expiration}")
    private int accessTokenExpirationTime;
    @Value("${x.jwt.refresh.expiration}")
    private int refreshTokenExpirationTime;

    @PostConstruct
    public void init() {
        // jwtKey가 주입된 후에 SecretKeySpec 생성
        this.key = new SecretKeySpec(jwtKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken(String email) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + accessTokenExpirationTime);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // 이렇게 만들거나 UUID로 만들어서 레디스에 저장하기
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();

    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(String token) {
        try {
            parseToken(token);
        } catch (ExpiredJwtException e) {
            log.warn("JwtProvider: Expired token");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("JwtProvider: Unsupported token");
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("JwtProvider: Malformed token");
            throw e;
        } catch (SignatureException e) {
            log.warn("JwtProvider: Signature exception");
            throw e;
        } catch (IllegalArgumentException e) {
            log.warn("JwtProvider: IllegalArgumentException");
            throw e;
        }
    }

    public JwtResponse reGenerateToken(String refreshToken, String email) {

        return null;
    }


    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}
