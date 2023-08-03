package org.youcancook.gobong.domain.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import org.youcancook.gobong.domain.authentication.repository.TemporaryTokenRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemporaryTokenService {

    @Value("${token.temporary-token-expiration-seconds}")
    private Integer temporaryTokenExpirationSeconds;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final Clock clock;

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
        return LocalDateTime.now(clock).plusSeconds(temporaryTokenExpirationSeconds);
    }

    @Transactional(readOnly = true)
    public boolean isExistTemporaryToken(String token) {
        LocalDateTime localDateTimeNow = LocalDateTime.now(clock);
        return temporaryTokenRepository.existsByTokenAndExpiredAtBefore(token, localDateTimeNow);
    }
}
