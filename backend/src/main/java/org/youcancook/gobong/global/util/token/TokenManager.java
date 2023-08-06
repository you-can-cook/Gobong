package org.youcancook.gobong.global.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.youcancook.gobong.global.util.clock.ClockService;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenManager {

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.access-token-expiration-seconds}")
    private Long accessTokenExpirationSeconds;

    @Value("${token.refresh-token-expiration-seconds}")
    private Long refreshTokenExpirationSeconds;

    private final ClockService clockService;

    public TokenDto createTokenDto(Long userId) {
        Date accessTokenExpiredAt = createAccessTokenExpirationTime();
        Date refreshTokenExpiredAt = createRefreshTokenExpirationTime();

        String accessToken = createAccessToken(userId, accessTokenExpiredAt);
        String refreshToken = createRefreshToken(userId, refreshTokenExpiredAt);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredAt(accessTokenExpiredAt)
                .refreshTokenExpiredAt(refreshTokenExpiredAt)
                .build();
    }

    private Date createAccessTokenExpirationTime() {
        Date now = clockService.getCurrentDate();
        return new Date(now.getTime() + accessTokenExpirationSeconds * 1000);
    }

    private Date createRefreshTokenExpirationTime() {
        Date now = clockService.getCurrentDate();
        return new Date(now.getTime() + refreshTokenExpirationSeconds * 1000);
    }

    private String createAccessToken(Long userId, Date expiredAt) {
        Claims claims = createClaims(userId, TokenType.ACCESS);
        return buildJwt(expiredAt, claims);
    }

    private String createRefreshToken(Long userId, Date expiredAt) {
        Claims claims = createClaims(userId, TokenType.REFRESH);
        return buildJwt(expiredAt, claims);
    }

    private Claims createClaims(Long userId, TokenType access) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put(Claims.SUBJECT, access.name());
        claims.put(Claims.ISSUER, "gobong.youcancook.org");
        return claims;
    }

    private String buildJwt(Date expiredAt, Claims claims) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(clockService.getCurrentDate())
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}