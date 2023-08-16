package org.youcancook.gobong.domain.recipe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.youcancook.gobong.domain.recipe.dto.response.GetFeedResponse;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.service.ServiceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class GetRecipeServiceTest {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    GetRecipeService getRecipeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeDetailRepository recipeDetailRepository;

    @Test
    public void getFeed(){
        User user = new User("name", "abc", OAuthProvider.GOOGLE, null);
        Long userId = userRepository.save(user).getId();

        Recipe recipe1 = new Recipe(user,"주먹밥1", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe2 = new Recipe(user,"주먹밥2", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe3 = new Recipe(user,"주먹밥3", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe4 = new Recipe(user,"주먹밥4", "주먹밥을 만들자", "밥", Difficulty.EASY, null);

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);
        Long lastId = recipeRepository.save(recipe4).getId();

        List<Long> recipeIds = recipeRepository.findAll().stream().map(Recipe::getId).toList();

        GetFeedResponse feedResponse = getRecipeService.getAllFeed(userId, null, 3);

        assertThat(feedResponse.getFeed()).hasSize(3);
        assertThat(feedResponse.isHasNext()).isTrue();

        Long lastRecipeId = feedResponse.getFeed().get(2).getId();
        GetFeedResponse actual = getRecipeService.getAllFeed(userId, lastRecipeId, 3);
        assertThat(actual.getFeed()).hasSize(1);
        assertThat(actual.isHasNext()).isFalse();

    }

}
