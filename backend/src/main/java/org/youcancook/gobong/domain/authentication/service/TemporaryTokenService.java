package org.youcancook.gobong.domain.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import org.youcancook.gobong.domain.authentication.exception.TemporaryTokenNotFoundException;
import org.youcancook.gobong.domain.authentication.repository.TemporaryTokenRepository;
import org.youcancook.gobong.global.util.clock.ClockService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemporaryTokenService {

    @Value("${token.temporary-token-expiration-seconds}")
    private Integer temporaryTokenExpirationSeconds;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final ClockService clockService;

    @Transactional
    public String saveTemporaryToken() {
        String token = createToken();
        LocalDateTime expiredAt = createExpiredAt();
        TemporaryToken temporaryToken = new TemporaryToken(token, expiredAt);
        temporaryTokenRepository.save(temporaryToken);
        return token;
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    private LocalDateTime createExpiredAt() {
        return clockService.getCurrentDateTime().plusSeconds(temporaryTokenExpirationSeconds);
    }

    @Transactional
    public void validateTemporaryToken(String token) {
        LocalDateTime localDateTimeNow = clockService.getCurrentDateTime();
        temporaryTokenRepository.findByTokenAndExpiredAtAfter(token, localDateTimeNow)
                .ifPresentOrElse(
                        temporaryTokenRepository::delete,
                        () -> {
                            throw new TemporaryTokenNotFoundException();
                        }
                );
    }
}
