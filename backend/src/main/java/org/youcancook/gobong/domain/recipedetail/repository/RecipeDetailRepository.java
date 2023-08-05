package org.youcancook.gobong.domain.recipedetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.recipedetail.entity.RecipeDetail;

public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Long> {
}
