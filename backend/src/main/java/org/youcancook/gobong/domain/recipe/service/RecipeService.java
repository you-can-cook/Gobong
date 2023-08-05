package org.youcancook.gobong.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.recipe.dto.response.CreateRecipeResponse;
import org.youcancook.gobong.domain.recipe.entity.Difficulty;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeAccessDeniedException;
import org.youcancook.gobong.domain.recipe.exception.RecipeNotFoundException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateRecipeResponse createRecipe(Long userId, String title, String introduction, List<String> ingredients,
                                             Difficulty difficulty, String thumbnailURL){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String parsedIngredients = String.join(",", ingredients);
        Recipe recipe = new Recipe(user, title, introduction, parsedIngredients, difficulty, thumbnailURL);

        Long recipeId = recipeRepository.save(recipe).getId();
        return new CreateRecipeResponse(recipeId);
    }

    @Transactional
    public void updateRecipe(Long userId, Long recipeId, String title, String introduction, List<String> ingredients,
                             Difficulty difficulty, String thumbnailURL){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        validateUserRecipe(user, recipe);

        String parsedIngredients = String.join(",", ingredients);
        recipe.updateProperties(title, introduction, parsedIngredients, difficulty, thumbnailURL);
    }



    public void validateUserRecipe(User user, Recipe recipe){
        if (!Objects.equals(user.getId(), recipe.getUser().getId()))
            throw new RecipeAccessDeniedException();
    }
}
