package org.youcancook.gobong.domain.rating.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.youcancook.gobong.domain.rating.entity.Rating;
import org.youcancook.gobong.domain.rating.exception.RatingMyRecipeException;
import org.youcancook.gobong.domain.rating.repository.RatingRepository;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.service.ServiceTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


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
        User user2 = User.builder().nickname("쩝쩝박사123").oAuthId("1234")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        Recipe recipe = Recipe.builder().user(user)
                .title("주먹밥").difficulty(Difficulty.EASY).build();

        userRepository.save(user);
        Long user2Id = userRepository.save(user2).getId();

        Long recipeId = recipeRepository.save(recipe).getId();

        ratingService.createRating(user2Id, recipeId, 3);
        List<Rating> actual = ratingRepository.findAll();

        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(actual.get(0).getScore()).isEqualTo(3);
    }

    @Test
    @DisplayName("성공적으로 평점을 수정한다.")
    public void updateRateTest(){
        User user = User.builder().nickname("쩝쩝박사").oAuthId("123")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        User user2 = User.builder().nickname("쩝쩝박사123").oAuthId("1234")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        Recipe recipe = Recipe.builder().user(user)
                .title("주먹밥").difficulty(Difficulty.EASY).build();

        userRepository.save(user);
        Long user2Id = userRepository.save(user2).getId();

        Long recipeId = recipeRepository.save(recipe).getId();

        Rating rating = new Rating(user2, recipe, 3);
        Long ratingId = ratingRepository.save(rating).getId();

        ratingService.updateRating(user2Id, recipeId, 5);
        Optional<Rating> actual = ratingRepository.findById(ratingId);

        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(actual.get().getScore()).isEqualTo(5);
    }

    @Test
    @DisplayName("자신의 레시피에 평점을 남기려고 하면 예외를 발생한다.")
    public void rateMyRecipe(){
        User user = User.builder().nickname("쩝쩝박사").oAuthId("123")
                .oAuthProvider(OAuthProvider.GOOGLE).build();
        Recipe recipe = Recipe.builder().user(user)
                .title("주먹밥").difficulty(Difficulty.EASY).build();

        Long userId = userRepository.save(user).getId();
        Long recipeId = recipeRepository.save(recipe).getId();

        assertThrows(RatingMyRecipeException.class, () -> ratingService.createRating(userId, recipeId, 5));
    }

    @AfterEach
    public void tearDown(){
        ratingRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }
}