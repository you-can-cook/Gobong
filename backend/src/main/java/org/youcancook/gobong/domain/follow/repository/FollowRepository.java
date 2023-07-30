package org.youcancook.gobong.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
