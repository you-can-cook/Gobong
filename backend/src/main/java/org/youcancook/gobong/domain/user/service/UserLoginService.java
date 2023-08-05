package org.youcancook.gobong.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.entity.RefreshToken;
import org.youcancook.gobong.domain.authentication.repository.RefreshTokenRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.token.TokenDto;
import org.youcancook.gobong.global.util.token.TokenManager;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenManager tokenManager;

    @Transactional
    public TokenDto login(String provider, String oAuthId) {
        OAuthProvider oAuthProvider = OAuthProvider.convertToEnum(provider);
        User user = userRepository.findByOAuthProviderAndOAuthId(oAuthProvider, oAuthId)
                .orElseThrow(UserNotFoundException::new);
        TokenDto tokenDto = tokenManager.createTokenDto(user.getId());
        saveRefreshToken(user.getId(), tokenDto);
        return tokenDto;
    }

    private void saveRefreshToken(Long userId, TokenDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .refreshToken(tokenDto.getRefreshToken())
                .expiredAt(tokenDto.getRefreshTokenExpiredAt())
                .build();
        refreshTokenRepository.save(refreshToken);
    }
}
