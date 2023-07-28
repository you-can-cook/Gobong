package org.youcancook.gobong.global.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.youcancook.gobong.domain.user.dto.response.JwtTokenDto;
import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.AuthenticationException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManager {

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.access-token-expiration}")
    private String accessTokenExpiration;

    public JwtTokenDto createTokenDto(Long userId) {
        String accessToken = createAccessToken(userId);
        return JwtTokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    private String createAccessToken(Long userId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("accessToken")
                .setAudience(String.valueOf(userId))
                .setIssuer("gobong.youcancook.org")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpiration)))
                .signWith(SignatureAlgorithm.HS256, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Long getUserId(String token) {
        validateToken(token);
        String strUserId = Jwts.parser()
                .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody().getAudience();
        return Long.valueOf(strUserId);
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.error("토큰 기한 만료", e);
            throw new AuthenticationException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {  // 토큰 변조
            log.error("잘못된 jwt token", e);
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        } catch (Exception e) {
            log.error("jwt token 검증 중 에러 발생", e);
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }
    }
}