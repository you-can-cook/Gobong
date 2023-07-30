package org.youcancook.gobong.domain.recipe.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookingTime {
    UNDER_10("10분 이내"), UNDER_30("30분 이내"), UNDER_60("60분 이내"), OVER_60("60분 이상");

    private final String description;
}
