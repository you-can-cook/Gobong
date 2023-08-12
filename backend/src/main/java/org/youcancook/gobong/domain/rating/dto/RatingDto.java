package org.youcancook.gobong.domain.rating.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RatingDto {
    private Long recipeId;
    private Double averageScore;
}
