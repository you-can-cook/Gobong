package org.youcancook.gobong.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.rating.entity.Rating;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserIdAndRecipeId(Long userId, Long recipeId);
}
