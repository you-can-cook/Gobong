package org.youcancook.gobong.domain.rating.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.youcancook.gobong.domain.rating.entity.Rating;
import org.youcancook.gobong.domain.rating.repository.RatingRepository;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.service.ServiceTest;

import java.util.Optional;


@ServiceTest
class RatingServiceTest {
    @Autowired
    RatingService ratingService;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Test
    @DisplayName("성공적으로 평점을 등록한다.")
    public void rateRecipeTest(){
        User user = User.builder().nickname("쩝쩝박사").oAuthId("123")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        Recipe recipe = Recipe.builder()
                .title("주먹밥").difficulty(Difficulty.EASY).build();

        Long userId = userRepository.save(user).getId();
        Long recipeId = recipeRepository.save(recipe).getId();

        Long ratingId = ratingService.createRating(userId, recipeId, 3).getId();
        Optional<Rating> actual = ratingRepository.findById(ratingId);

        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(actual.get().getScore()).isEqualTo(3);
    }

    @Test
    @DisplayName("성공적으로 평점을 수정한다.")
    public void updateRateTest(){
        User user = User.builder().nickname("쩝쩝박사").oAuthId("123")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        Recipe recipe = Recipe.builder()
                .title("주먹밥").difficulty(Difficulty.EASY).build();

        Long userId = userRepository.save(user).getId();
        Long recipeId = recipeRepository.save(recipe).getId();

        Rating rating = new Rating(user, recipe, 3);
        Long ratingId = ratingRepository.save(rating).getId();

        ratingService.updateRating(userId, recipeId, 5);
        Optional<Rating> actual = ratingRepository.findById(ratingId);

        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(actual.get().getScore()).isEqualTo(5);
    }

    @AfterEach
    public void tearDown(){
        ratingRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }
}