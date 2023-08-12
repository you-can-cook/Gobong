package org.youcancook.gobong.domain.recipe.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookmarkedRecipesResponse {
    private boolean isLast;
    private List<BookmarkedRecipeResponse> recipes;
}
