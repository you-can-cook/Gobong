package org.youcancook.gobong.domain.bookmarkrecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;

import java.util.Optional;

public interface BookmarkRecipeRepository extends JpaRepository<BookmarkRecipe, Long> {
    Optional<BookmarkRecipe> findByUserIdAndRecipeId(Long userId, Long recipeId);

    Boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

}
