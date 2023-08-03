package org.youcancook.gobong.domain.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import java.time.LocalDateTime;

public interface TemporaryTokenRepository extends JpaRepository<TemporaryToken, Long> {
    boolean existsByTokenAndExpiredAtBefore(String token, LocalDateTime expiredAt);
}
