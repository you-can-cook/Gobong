package org.youcancook.gobong.domain.recipe.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.user.entity.User;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "JOIN FETCH RecipeDetail rd ON rd.recipe.id = r.id " +
            "JOIN FETCH r.user u " +
            "WHERE r.id =:recipeId")
    Optional<Recipe> fetchFindById(Long recipeId);

    @Query("SELECT r FROM Recipe r WHERE r.id <:recipeId")
    Slice<Recipe> getAllFeed(Long recipeId, PageRequest of);

    @Query("SELECT r FROM BookmarkRecipe br " +
            "JOIN br.recipe r " +
            "WHERE r.id <:recipeId AND br.user.id =:userId")
    Slice<Recipe> getAllBookmarkedFeed(Long userId, Long recipeId, PageRequest of);

    @Query("SELECT r FROM Recipe r " +
            "WHERE (r.user.id IN (SELECT f.followee.id FROM Follow f WHERE f.follower.id =:userId) OR r.user.id =:userId) " +
            "AND r.id <:recipeId")
    Slice<Recipe> getAllFollowingFeed(Long userId, long recipeId, PageRequest of);

    @Query("SELECT r FROM Recipe r " +
            "WHERE r.user.id =:userId " +
            "AND r.id <:recipeId")
    Slice<Recipe> getAllUserFeed(Long userId, long recipeId, PageRequest of);
  
    Long countByUser(User user);
}
