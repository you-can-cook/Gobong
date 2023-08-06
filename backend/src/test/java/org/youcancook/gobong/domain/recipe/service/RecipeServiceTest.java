package org.youcancook.gobong.domain.recipe.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.youcancook.gobong.domain.recipe.dto.request.CreateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.request.UpdateRecipeRequest;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeAccessDeniedException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.dto.request.UploadRecipeDetailRequest;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RecipeServiceTest {
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeDetailRepository recipeDetailRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("레시피를 성공적으로 등록한다.")
    public void createRecipe(){
        User user = User.builder().nickname("쩝쩝박사").oAuthProvider(OAuthProvider.GOOGLE).oAuthId("123").build();
        Long userId = userRepository.save(user).getId();
        String title = "주먹밥";
        List<UploadRecipeDetailRequest> details = new ArrayList<>();
        recipeService.createRecipe(userId, new CreateRecipeRequest(title, "주먹밥을 만들어요",
                List.of("밥", "김"), "쉬워요", null, details));
        List<Recipe> actual = recipeRepository.findAll();

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("레시피 내용을 성공적으로 업데이트한다.")
    public void updateRecipe(){
        User user = User.builder().nickname("쩝쩝박사").oAuthProvider(OAuthProvider.GOOGLE).oAuthId("123").build();
        Recipe recipe = Recipe.builder().user(user).difficulty(Difficulty.EASY).title("주먹밥").build();

        Long userId = userRepository.save(user).getId();
        Long recipeId = recipeRepository.save(recipe).getId();
        String title = "비빔밥";
        List<UploadRecipeDetailRequest> details = new ArrayList<>();

        recipeService.updateRecipe(userId, new UpdateRecipeRequest(recipeId, title, "주먹밥을 만들어요",
                List.of("밥", "김"), "보통이에요", null, details));

        List<Recipe> actual = recipeRepository.findAll();
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("작성자가 아닌 사람이 업데이트 시도 시 예외를 반환한다.")
    public void unauthorizedTest(){
        User user1 = User.builder().nickname("쩝쩝박사").oAuthProvider(OAuthProvider.GOOGLE).oAuthId("123").build();
        User user2 = User.builder().nickname("쩝쩝학사").oAuthProvider(OAuthProvider.KAKAO).oAuthId("125").build();
        Recipe recipe = Recipe.builder().user(user1).difficulty(Difficulty.EASY).title("주먹밥").build();

        Long user1Id = userRepository.save(user1).getId();
        Long user2Id = userRepository.save(user2).getId();
        Long recipeId = recipeRepository.save(recipe).getId();
        String title = "비빔밥";
        List<UploadRecipeDetailRequest> details = new ArrayList<>();
        assertThrows(RecipeAccessDeniedException.class, ()->
            recipeService.updateRecipe(user2Id, new UpdateRecipeRequest(recipeId, title, "주먹밥을 만들어요",
                List.of("밥", "김"), "어려워요", null, details)));
    }


    @AfterEach
    void teardown(){
        recipeDetailRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }
}