package org.youcancook.gobong.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.bookmarkrecipe.repository.BookmarkRecipeRepository;
import org.youcancook.gobong.domain.rating.repository.RatingRepository;
import org.youcancook.gobong.domain.recipe.dto.request.CreateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.request.UpdateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.response.BookmarkedRecipeResponse;
import org.youcancook.gobong.domain.recipe.dto.response.BookmarkedRecipesResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final BookmarkRecipeRepository bookmarkRecipeRepository;

    @Transactional
    public CreateRecipeResponse createRecipe(Long userId, CreateRecipeRequest request){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String parsedIngredients = String.join(",", request.getIngredients());
        Recipe recipe = new Recipe(user, request.getTitle(), request.getIntroduction(),
                parsedIngredients, Difficulty.from(request.getDifficulty()), request.getThumbnailURL());

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
                parsedIngredients, Difficulty.from(request.getDifficulty()), request.getThumbnailURL());
    }

    @Transactional
    public void deleteRecipe(Long userId, Long recipeId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        validateUserRecipe(user, recipe);

        bookmarkRecipeRepository.deleteAllByRecipeId(recipeId);
        recipeDetailRepository.deleteAllByRecipeId(recipeId);
        ratingRepository.deleteAllByRecipeId(recipeId);
        recipeRepository.delete(recipe);
    }

    public void validateUserRecipe(User user, Recipe recipe){
        if (!Objects.equals(user.getId(), recipe.getUser().getId()))
            throw new RecipeAccessDeniedException();
    }

    public BookmarkedRecipesResponse getAllBookmarkedRecipes(Long userId, Integer page, int count){
        Slice<Recipe> bookmarkedRecipes = recipeRepository.findByBookmarkedRecipes(
                userId,
                PageRequest.of(page, count, Sort.by("createdAt").descending())
        );

        List<BookmarkedRecipeResponse> responses = bookmarkedRecipes.getContent().stream()
                .map(recipe -> new BookmarkedRecipeResponse(
                        recipe.getId(),
                        recipe.getUser().getId(),
                        recipe.getTotalCookTimeInSeconds(),
                        recipe.getThumbnailURL(),
                        recipe.getDifficulty().getDescription(),
                        recipe.getAverageRating(),
                        recipe.getCookwares(),
                        recipe.getBookmarks().size()
                ))
                .collect(Collectors.toList());
        return new BookmarkedRecipesResponse(bookmarkedRecipes.isLast(), responses);
    }
}
