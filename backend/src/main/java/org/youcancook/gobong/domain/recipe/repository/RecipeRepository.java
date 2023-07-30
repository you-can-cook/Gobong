package org.youcancook.gobong.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.recipe.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
