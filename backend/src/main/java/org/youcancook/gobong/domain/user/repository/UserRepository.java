package org.youcancook.gobong.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
