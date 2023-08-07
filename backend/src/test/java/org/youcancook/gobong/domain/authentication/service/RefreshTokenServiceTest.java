package org.youcancook.gobong.domain.authentication.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.youcancook.gobong.domain.authentication.entity.RefreshToken;
import org.youcancook.gobong.domain.authentication.repository.RefreshTokenRepository;
import org.youcancook.gobong.global.util.token.TokenDto;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("리프레시 토큰 저장")
    void saveRefreshToken() {
        // given
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .then(invocation -> invocation.getArgument(0));

        // when
        TokenDto tokenDto = createTestTokenDto();
        refreshTokenService.saveRefreshToken(1L, tokenDto);

        // then
        ArgumentCaptor<RefreshToken> argument = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository, times(1)).save(argument.capture());

        RefreshToken savedRefreshToken = argument.getValue();
        assertThat(savedRefreshToken.getUserId()).isEqualTo(1L);
        assertThat(savedRefreshToken.getRefreshToken()).isEqualTo(tokenDto.getRefreshToken());
        assertThat(savedRefreshToken.getExpiredAt()).isEqualTo(tokenDto.getRefreshTokenExpiredAt());
    }

    private TokenDto createTestTokenDto() {
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .accessTokenExpiredAt(new Date())
                .refreshTokenExpiredAt(new Date())
                .build();
    }
}