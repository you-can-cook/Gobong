package org.youcancook.gobong.domain.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.youcancook.gobong.domain.rating.dto.RatingDto;
import org.youcancook.gobong.domain.rating.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r join r.user u join r.recipe recipe WHERE u.id =:userId AND recipe.id =:recipeId")
    Optional<Rating> findByUserIdAndRecipeId(Long userId, Long recipeId);

    @Query("SELECT NEW org.youcancook.gobong.domain.rating.dto.RatingDto(recipe.id, AVG(r.score)) " +
            "FROM Rating r " +
            "join r.recipe recipe " +
            "WHERE recipe.id IN :recipeIds " +
            "GROUP BY recipe.id")
    List<RatingDto> getAverageRatings(List<Long> recipeIds);

    @Modifying
    @Query("DELETE FROM Rating r where r.recipe.id =:recipeId")
    void deleteAllByRecipeId(Long recipeId);
}
