package org.youcancook.gobong.domain.recipe.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookmarkedRecipeResponse {

    private Long recipeId;
    private Long userId;
    private Integer totalTimeInSeconds;
    private String thumbnailURL;
    private String difficulty;
    private Double averageRating;
    private long cookwares;
    private int totalBookmarks;

}
