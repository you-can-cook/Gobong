package org.youcancook.gobong.domain.bookmarkrecipe.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.youcancook.gobong.domain.bookmarkrecipe.dto.TotalBookmarkDto;
import org.youcancook.gobong.domain.bookmarkrecipe.entity.BookmarkRecipe;
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
class BookmarkRecipeRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    BookmarkRecipeRepository bookmarkRecipeRepository;

    @Test
    @DisplayName("레시피의 총 북마크 합을 올바르게 조회한다.")
    public void getTotalBookmarkCount(){
        User user1 = User.builder().nickname("abc").oAuthId("1231").oAuthProvider(OAuthProvider.GOOGLE).build();
        User user2 = User.builder().nickname("def").oAuthId("1232").oAuthProvider(OAuthProvider.GOOGLE).build();
        User user3 = User.builder().nickname("ghi").oAuthId("1233").oAuthProvider(OAuthProvider.GOOGLE).build();
        User user4 = User.builder().nickname("ghdd").oAuthId("1234").oAuthProvider(OAuthProvider.GOOGLE).build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Recipe recipe1 = Recipe.builder().title("주먹밥1").difficulty(Difficulty.EASY).user(user1).build();
        Recipe recipe2 = Recipe.builder().title("주먹밥2").difficulty(Difficulty.EASY).user(user1).build();
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        bookmarkRecipeRepository.save(new BookmarkRecipe(user2, recipe1));
        bookmarkRecipeRepository.save(new BookmarkRecipe(user3, recipe1));
        bookmarkRecipeRepository.save(new BookmarkRecipe(user4, recipe1));

        bookmarkRecipeRepository.save(new BookmarkRecipe(user2, recipe2));
        bookmarkRecipeRepository.save(new BookmarkRecipe(user3, recipe2));

        List<Long> recipeIds = List.of(recipe1.getId(), recipe2.getId());
        List<TotalBookmarkDto> totalBookmarks = bookmarkRecipeRepository.getTotalBookmarks(recipeIds);
        Map<Long, Long> actual = totalBookmarks.stream()
                .collect(Collectors.toMap(
                        TotalBookmarkDto::getRecipeId,
                        TotalBookmarkDto::getTotalBookmarkCount
                ));

        assertThat(actual).hasSize(2);
        assertThat(actual.get(recipe1.getId())).isEqualTo(3);
        assertThat(actual.get(recipe2.getId())).isEqualTo(2);
    }

}