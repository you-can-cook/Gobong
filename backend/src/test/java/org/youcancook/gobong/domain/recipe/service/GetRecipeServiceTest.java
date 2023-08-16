package org.youcancook.gobong.domain.recipe.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;
import org.youcancook.gobong.domain.bookmarkrecipe.repository.BookmarkRecipeRepository;
import org.youcancook.gobong.domain.recipe.dto.response.GetFeedResponse;
import org.youcancook.gobong.domain.recipe.dto.response.GetRecipeSummaryResponse;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.service.ServiceTest;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    BookmarkRecipeRepository bookmarkRecipeRepository;

    @Test
    @DisplayName("전체 피드를 성공적으로 가져온다.")
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
        recipeRepository.save(recipe4);

        GetFeedResponse feedResponse = getRecipeService.getAllFeed(userId, null, 3);

        assertThat(feedResponse.getFeed()).hasSize(3);
        assertThat(feedResponse.isHasNext()).isTrue();

        Long lastRecipeId = feedResponse.getFeed().get(2).getId();
        GetFeedResponse actual = getRecipeService.getAllFeed(userId, lastRecipeId, 3);
        assertThat(actual.getFeed()).hasSize(1);
        assertThat(actual.isHasNext()).isFalse();

    }

    @Test
    @DisplayName("북마크 피드를 성공적으로 가져온다.")
    public void getBookmarkedFeed(){
        User user = new User("name", "abc", OAuthProvider.GOOGLE, null);
        User user2 = new User("name1", "abcd", OAuthProvider.GOOGLE, null);
        userRepository.save(user);
        Long user2Id = userRepository.save(user2).getId();

        Recipe recipe1 = new Recipe(user,"주먹밥1", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe2 = new Recipe(user,"주먹밥2", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe3 = new Recipe(user,"주먹밥3", "주먹밥을 만들자", "밥", Difficulty.EASY, null);
        Recipe recipe4 = new Recipe(user,"주먹밥4", "주먹밥을 만들자", "밥", Difficulty.EASY, null);

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);
        recipeRepository.save(recipe4);

        BookmarkRecipe bookmarkRecipe1 = new BookmarkRecipe(user2, recipe1);
        BookmarkRecipe bookmarkRecipe2 = new BookmarkRecipe(user2, recipe2);
        bookmarkRecipeRepository.save(bookmarkRecipe1);
        bookmarkRecipeRepository.save(bookmarkRecipe2);

        GetFeedResponse feedResponse = getRecipeService.getBookmarkedFeed(user2Id, null, 3);

        assertThat(feedResponse.getFeed()).hasSize(2);
        assertThat(feedResponse.isHasNext()).isFalse();

        List<String> titles = feedResponse.getFeed().stream().map(GetRecipeSummaryResponse::getTitle).collect(Collectors.toList());
        assertThat(titles).hasSize(2);
        assertThat(titles).containsAll(List.of("주먹밥1", "주먹밥2"));
    }


}
