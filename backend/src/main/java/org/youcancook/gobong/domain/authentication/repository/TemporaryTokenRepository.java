package org.youcancook.gobong.domain.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TemporaryTokenRepository extends JpaRepository<TemporaryToken, Long> {

    boolean existsByTokenAndExpiredAtBefore(String token, LocalDateTime expiredAt);
}
