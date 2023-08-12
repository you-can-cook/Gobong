package org.youcancook.gobong.domain.recipe.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.youcancook.gobong.domain.recipe.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM BookmarkRecipe b join b.recipe r join b.user u where u.id =:userId")
    Slice<Recipe> findByBookmarkedRecipes(Long userId, PageRequest of);
}
