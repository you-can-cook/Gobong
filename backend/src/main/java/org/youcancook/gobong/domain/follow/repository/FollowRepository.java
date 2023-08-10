package org.youcancook.gobong.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.follow.entity.Follow;
import org.youcancook.gobong.domain.user.entity.User;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowee(User follower, User followee);

    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
}
