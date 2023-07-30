package org.youcancook.gobong.domain.bookmarkrecipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;

public interface BookmarkRecipeRepository extends JpaRepository<BookmarkRecipe, Long> {
}
