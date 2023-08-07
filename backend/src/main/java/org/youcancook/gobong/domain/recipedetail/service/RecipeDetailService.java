package org.youcancook.gobong.domain.recipedetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.recipe.exception.RecipeNotFoundException;
import org.youcancook.gobong.domain.recipe.repository.RecipeRepository;
import org.youcancook.gobong.domain.recipedetail.dto.request.UploadRecipeDetailRequest;
import org.youcancook.gobong.domain.recipedetail.entity.RecipeDetail;
import org.youcancook.gobong.domain.recipedetail.repository.RecipeDetailRepository;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeDetailService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;

    @Transactional
    public void uploadRecipeDetails(Long recipeId, List<UploadRecipeDetailRequest> requests){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        clearRecipeDetails(recipe);

        IntStream.range(0, requests.size()).forEach(index -> {
            UploadRecipeDetailRequest request = requests.get(index);
            uploadRecipeDetail(recipe, request.getContent(), request.getImageURL(),
                    request.getCookTimeInSeconds(), request.getCookware(), index);
        });

        recipe.gatherFromDetails();
    }

    @Transactional
    public void uploadRecipeDetail(Recipe recipe, String content, String imageURL,
                                   int cookTimeInSeconds, long cookware, int step){
        RecipeDetail recipeDetail = new RecipeDetail(recipe, content, imageURL, cookTimeInSeconds, cookware, step);
        recipeDetailRepository.save(recipeDetail);
    }

    @Transactional
    public void clearRecipeDetails(Recipe recipe){
        recipeDetailRepository.deleteAllByRecipeId(recipe.getId());
        recipe.clearDetails();
    }

}
