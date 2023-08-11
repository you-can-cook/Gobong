package org.youcancook.gobong.domain.recipe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcancook.gobong.domain.recipe.dto.request.CreateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.request.UpdateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.response.CreateRecipeResponse;
import org.youcancook.gobong.domain.recipe.service.RecipeService;
import org.youcancook.gobong.global.resolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<CreateRecipeResponse> createRecipe(@LoginUserId Long userId,
                                                             @RequestBody @Valid CreateRecipeRequest request){
        CreateRecipeResponse response = recipeService.createRecipe(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{recipeId}/update")
    public ResponseEntity<Void> updateRecipe(@LoginUserId Long userId,
                                             @RequestBody @Valid UpdateRecipeRequest request){
        recipeService.updateRecipe(userId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@LoginUserId Long userId, @PathVariable Long recipeId){

        recipeService.deleteRecipe(userId, recipeId);
        return ResponseEntity.ok().build();
    }
}
