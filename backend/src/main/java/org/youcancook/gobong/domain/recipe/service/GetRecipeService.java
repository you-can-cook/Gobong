package org.youcancook.gobong.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.follow.service.FollowService;
import org.youcancook.gobong.domain.recipe.dto.recipeDto.RecipeDto;
import org.youcancook.gobong.domain.recipe.dto.response.GetFeedResponse;
import org.youcancook.gobong.domain.recipe.dto.response.GetRecipeResponse;
import org.youcancook.gobong.domain.recipe.dto.response.GetRecipeSummaryResponse;
import org.youcancook.gobong.domain.recipe.dto.response.RecipeAuthorResponse;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeNotFoundException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.dto.response.GetRecipeDetailResponse;
import org.youcancook.gobong.domain.recipedetail.service.RecipeDetailService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetRecipeService {

    private final RecipeService recipeService;
    private final FollowService followService;
    private final RecipeDetailService recipeDetailService;
    private final RecipeRepository recipeRepository;

    public GetRecipeResponse getRecipe(Long userId, Long recipeId){
        Recipe recipe = recipeRepository.fetchFindById(recipeId).orElseThrow(RecipeNotFoundException::new);
        RecipeDto recipeDto = RecipeDto.from(recipe);
        List<GetRecipeDetailResponse> recipeDetails = recipeDetailService.getRecipeDetails(recipe);
        GetRecipeSummaryResponse summaryResponse = getSummary(userId, recipeDto);

        return new GetRecipeResponse(recipeDto.getId(), summaryResponse,
                recipeDto.getIntroduction(), recipeDto.getIngredients(), recipeDetails);
    }

    public GetRecipeSummaryResponse getSummary(Long userId, RecipeDto recipeDto){
        RecipeAuthorResponse authorResponse = getAuthor(userId, recipeDto);
        return new GetRecipeSummaryResponse(
                recipeDto.getId(), recipeDto.getTitle(), recipeDto.getThumbnailURL(), authorResponse,
                recipeDto.getTotalBookmarkCount(), recipeDto.getTotalCookTimeInSeconds(), recipeDto.getCookwares(),
                recipeDto.getDifficulty(), recipeDto.getAverageRating()
        );
    }

    public RecipeAuthorResponse getAuthor(Long userId, RecipeDto recipeDto){
        Long authorId = recipeDto.getAuthorId();
        String authorNickname = recipeDto.getAuthorName();
        boolean isFollowing = followService.isFollowing(userId, authorId);
        boolean isMyself = Objects.equals(userId, authorId);

        return new RecipeAuthorResponse(authorId, authorNickname, isFollowing, isMyself);
    }

    public GetFeedResponse getAllFeed(Long userId, long recipeId, int count){
        Slice<Recipe> feedRecipes = recipeRepository.getAllFeed(recipeId,
                PageRequest.of(0, count, Sort.by("id").descending()));
        return getFeedResponse(userId, feedRecipes);
    }

    public GetFeedResponse getBookmarkedFeed(Long userId, long recipeId, int count){
        Slice<Recipe> feedRecipes = recipeRepository.getAllBookmarkedFeed(userId, recipeId,
                PageRequest.of(0, count, Sort.by("id").descending()));
        return getFeedResponse(userId, feedRecipes);
    }

    public GetFeedResponse getFollowingFeed(Long userId, long recipeId, int count){
        Slice<Recipe> feedRecipes = recipeRepository.getAllFollowingFeed(userId, recipeId,
                PageRequest.of(0, count, Sort.by("id").descending()));
        return getFeedResponse(userId, feedRecipes);
    }

    private GetFeedResponse getFeedResponse(Long userId, Slice<Recipe> feedRecipes){
        List<GetRecipeSummaryResponse> summaries = feedRecipes.getContent().stream()
                .map(recipe -> getSummary(userId, RecipeDto.from(recipe)))
                .toList();
        return new GetFeedResponse(summaries, feedRecipes.hasNext());
    }
}
