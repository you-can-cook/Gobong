package org.youcancook.gobong.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.rating.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
