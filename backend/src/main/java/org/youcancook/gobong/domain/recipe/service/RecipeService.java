package org.youcancook.gobong.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.recipe.dto.request.CreateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.request.UpdateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.response.CreateRecipeResponse;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeAccessDeniedException;
import org.youcancook.gobong.domain.recipe.exception.RecipeNotFoundException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateRecipeResponse createRecipe(Long userId, CreateRecipeRequest request){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String parsedIngredients = String.join(",", request.getIngredients());
        Recipe recipe = new Recipe(user, request.getTitle(), request.getIntroduction(),
                parsedIngredients, request.getDifficulty(), request.getThumbnailURL());

        Long recipeId = recipeRepository.save(recipe).getId();
        return new CreateRecipeResponse(recipeId);
    }

    @Transactional
    public void updateRecipe(Long userId, UpdateRecipeRequest request){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(request.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        validateUserRecipe(user, recipe);

        String parsedIngredients = String.join(",", request.getIngredients());
        recipe.updateProperties(request.getTitle(), request.getIntroduction(),
                parsedIngredients, request.getDifficulty(), request.getThumbnailURL());
    }

    public void validateUserRecipe(User user, Recipe recipe){
        if (!Objects.equals(user.getId(), recipe.getUser().getId()))
            throw new RecipeAccessDeniedException();
    }
}
