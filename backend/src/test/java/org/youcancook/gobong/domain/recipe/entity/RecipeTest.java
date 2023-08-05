package org.youcancook.gobong.domain.recipe.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.youcancook.gobong.domain.recipedetail.entity.RecipeDetail;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeTest {

    @Test
    @DisplayName("레시피 내 시간을 올바르게 계산한다.")
    public void gatherTotalTimeInSecond(){
        User user = User.builder().nickname("쩝쩝박사").oAuthProvider(OAuthProvider.GOOGLE).oAuthId("123").build();
        Recipe recipe = Recipe.builder()
                .title("주먹밥")
                .difficulty(Difficulty.EASY)
                .user(user)
                .build();

        for(int i = 1; i <= 10; i++){
            RecipeDetail.builder()
                    .recipe(recipe)
                    .cookTimeInSeconds(i)
                    .cookware(0L)
                    .build();
        }
        recipe.gatherTotalTimeInSeconds();
        assertThat(recipe.getTotalTimeInSeconds()).isEqualTo(55);
    }

    @Test
    @DisplayName("레시피 내 조리도구를 올바르게 계산한다.")
    void gatherCookwares() {
        User user = User.builder().nickname("쩝쩝박사").oAuthProvider(OAuthProvider.GOOGLE).oAuthId("123").build();
        Recipe recipe = Recipe.builder()
                .title("주먹밥")
                .difficulty(Difficulty.EASY)
                .user(user)
                .build();

        for(int i = 0; i < 10; i++){
            RecipeDetail.builder()
                    .recipe(recipe)
                    .cookTimeInSeconds(i)
                    .cookware(1L << i)
                    .build();
        }
        recipe.gatherCookwares();
        assertThat(recipe.getCookwares()).isEqualTo((1 << 10) - 1);
    }
}