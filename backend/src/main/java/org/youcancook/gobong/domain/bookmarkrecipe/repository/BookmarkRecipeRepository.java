package org.youcancook.gobong.domain.bookmarkrecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.youcancook.gobong.domain.bookmarkrecipe.dto.TotalBookmarkDto;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;

import java.util.List;
import java.util.Optional;

public interface BookmarkRecipeRepository extends JpaRepository<BookmarkRecipe, Long> {
    Optional<BookmarkRecipe> findByUserIdAndRecipeId(Long userId, Long recipeId);

    Boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    @Modifying
    @Query("DELETE FROM BookmarkRecipe bm where bm.recipe.id =:recipeId")
    void deleteAllByRecipeId(Long recipeId);

    @Query("SELECT NEW org.youcancook.gobong.domain.bookmarkrecipe.dto.TotalBookmarkDto(r.id, COUNT(*)) " +
            "FROM BookmarkRecipe bm " +
            "JOIN bm.recipe r " +
            "WHERE r.id IN :recipeIds " +
            "GROUP BY r.id")
    List<TotalBookmarkDto> getTotalBookmarks(List<Long> recipeIds);

}
