package org.youcancook.gobong.domain.rating.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.youcancook.gobong.domain.rating.dto.RatingDto;
import org.youcancook.gobong.domain.rating.entity.Rating;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RatingRepositoryTest {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Test
    @DisplayName("평균 평점을 올바르게 조회한다.")
    public void getAverageRating(){
        User user1 = User.builder().nickname("abc").oAuthId("1231").oAuthProvider(OAuthProvider.GOOGLE).build();
        User user2 = User.builder().nickname("def").oAuthId("1232").oAuthProvider(OAuthProvider.GOOGLE).build();
        User user3 = User.builder().nickname("ghi").oAuthId("1233").oAuthProvider(OAuthProvider.GOOGLE).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Recipe recipe1 = Recipe.builder().title("주먹밥1").difficulty(Difficulty.EASY).user(user1).build();
        Recipe recipe2 = Recipe.builder().title("주먹밥2").difficulty(Difficulty.EASY).user(user1).build();
        Recipe recipe3 = Recipe.builder().title("주먹밥3").difficulty(Difficulty.EASY).user(user1).build();
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);

        ratingRepository.save(new Rating(user2, recipe1, 1));
        ratingRepository.save(new Rating(user2, recipe2, 5));
        ratingRepository.save(new Rating(user2, recipe3, 5));

        ratingRepository.save(new Rating(user3, recipe1, 5));
        ratingRepository.save(new Rating(user3, recipe2, 4));
        ratingRepository.save(new Rating(user3, recipe3, 4));

        List<Long> ids = List.of(recipe1.getId(), recipe2.getId());
        Map<Long, Double> actual = ratingRepository.getAverageRatings(ids)
                .stream()
                .collect(Collectors.toMap(
                    RatingDto::getRecipeId,
                    RatingDto::getAverageScore
                ));

        assertThat(actual).hasSize(2);
        assertThat(actual.get(recipe1.getId())).isEqualTo(3.0);
        assertThat(actual.get(recipe2.getId())).isEqualTo(4.5);
    }
}