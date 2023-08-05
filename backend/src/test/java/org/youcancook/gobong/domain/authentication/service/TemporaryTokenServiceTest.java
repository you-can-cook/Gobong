package org.youcancook.gobong.domain.authentication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import org.youcancook.gobong.domain.authentication.exception.TemporaryTokenFoundException;
import org.youcancook.gobong.domain.authentication.repository.TemporaryTokenRepository;
import org.youcancook.gobong.global.util.clock.ClockService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemporaryTokenServiceTest {

    @InjectMocks
    private TemporaryTokenService temporaryTokenService;

    @Mock
    private ClockService clockService;

    @Mock
    private TemporaryTokenRepository temporaryTokenRepository;

    @BeforeEach
    void mockingClock() {
        final String localDateTimeOnTestEnv = "2023-07-16T10:15:30";
        when(clockService.getCurrentDateTime())
                .thenReturn(LocalDateTime.parse(localDateTimeOnTestEnv));
    }

    @Test
    @DisplayName("토큰 생성")
    void saveTemporaryToken() {
        // given
        when(temporaryTokenRepository.save(any(TemporaryToken.class)))
                .thenReturn(new TemporaryToken("token", LocalDateTime.now()));
        ReflectionTestUtils.setField(temporaryTokenService, "temporaryTokenExpirationSeconds", 600);

        // when
        String token = temporaryTokenService.saveTemporaryToken();

        // then
        String UUIDRegex = "[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}";
        assertThat(token).matches(UUIDRegex);

        ArgumentCaptor<TemporaryToken> argument = ArgumentCaptor.forClass(TemporaryToken.class);
        verify(temporaryTokenRepository, times(1)).save(argument.capture());
        assertThat(argument.getValue().getToken()).isEqualTo(token);

        LocalDateTime expectedExpiredAt = clockService.getCurrentDateTime().plusSeconds(600);
        assertThat(argument.getValue().getExpiredAt()).isEqualTo(expectedExpiredAt);
    }

    @Test
    @DisplayName("토큰 검증 - 토큰이 만료 되었을 때")
    void validTemporaryTokenFail() {
        // given
        final String token = UUID.randomUUID().toString();
        when(temporaryTokenRepository.findByTokenAndExpiredAtBefore(token, clockService.getCurrentDateTime()))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(TemporaryTokenFoundException.class,
                () -> temporaryTokenService.validTemporaryToken(token));

        verify(temporaryTokenRepository, times(1))
                .findByTokenAndExpiredAtBefore(token, clockService.getCurrentDateTime());
    }

    @Test
    @DisplayName("토큰 검증 - 토큰이 만료 되지 않았을 때")
    void validTemporaryTokenSuccess() {
        // given
        final String token = UUID.randomUUID().toString();
        when(temporaryTokenRepository.findByTokenAndExpiredAtBefore(token, clockService.getCurrentDateTime()))
                .thenReturn(Optional.of(new TemporaryToken(token, LocalDateTime.now())));
        doNothing().when(temporaryTokenRepository).delete(any(TemporaryToken.class));

        // when
        temporaryTokenService.validTemporaryToken(token);

        // then
        verify(temporaryTokenRepository, times(1))
                .findByTokenAndExpiredAtBefore(token, clockService.getCurrentDateTime());
        verify(temporaryTokenRepository, times(1))
                .delete(any(TemporaryToken.class));
    }
}