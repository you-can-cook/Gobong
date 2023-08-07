package org.youcancook.gobong.domain.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.entity.RefreshToken;
import org.youcancook.gobong.domain.authentication.repository.RefreshTokenRepository;
import org.youcancook.gobong.global.util.token.TokenDto;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(Long userId, TokenDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .refreshToken(tokenDto.getRefreshToken())
                .expiredAt(tokenDto.getRefreshTokenExpiredAt())
                .build();
        refreshTokenRepository.save(refreshToken);
    }
}
