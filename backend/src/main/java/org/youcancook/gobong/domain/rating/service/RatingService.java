package org.youcancook.gobong.domain.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.rating.dto.response.RateRecipeResponse;
import org.youcancook.gobong.domain.rating.entity.Rating;
import org.youcancook.gobong.domain.rating.exception.RatingNotFoundException;
import org.youcancook.gobong.domain.rating.repository.RatingRepository;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeNotFoundException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    public RateRecipeResponse createRating(Long userId, Long recipeId, Integer score){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        Rating rating = new Rating(user, recipe, score);
        Long ratingId = ratingRepository.save(rating).getId();
        return new RateRecipeResponse(ratingId);
    }

    @Transactional
    public void updateRating(Long userId, Long recipeId, Integer score){
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        Rating rating = ratingRepository.findByUserIdAndRecipeId(userId, recipeId)
                .orElseThrow(RatingNotFoundException::new);

        rating.updateScore(score);
    }

}
