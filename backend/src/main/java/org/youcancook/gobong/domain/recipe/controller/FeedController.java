package org.youcancook.gobong.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.youcancook.gobong.domain.recipe.dto.response.GetFeedResponse;
import org.youcancook.gobong.domain.recipe.service.GetRecipeService;
import org.youcancook.gobong.global.resolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed")
public class FeedController {

    private final GetRecipeService getRecipeService;

    @GetMapping("all")
    public ResponseEntity<GetFeedResponse> getFeed(@LoginUserId Long userId,
                                                   @RequestParam(name = "last", required = false) Long lastRecipeId,
                                                   @RequestParam(name = "count", required = true) int count){
        GetFeedResponse response = getRecipeService.getAllFeed(userId, lastRecipeId, count);
        return ResponseEntity.ok(response);
    }



}
