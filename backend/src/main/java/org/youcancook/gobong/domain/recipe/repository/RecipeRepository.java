package org.youcancook.gobong.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.youcancook.gobong.domain.recipe.entity.Recipe;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "JOIN FETCH RecipeDetail rd ON rd.recipe.id = r.id " +
            "JOIN FETCH r.user u " +
            "WHERE r.id =:recipeId")
    Optional<Recipe> fetchFindById(Long recipeId);
}
