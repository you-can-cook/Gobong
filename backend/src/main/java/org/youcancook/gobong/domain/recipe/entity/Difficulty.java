package org.youcancook.gobong.domain.recipe.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    EASY("쉬워요"), NORMAL("보통이에요"), HARD("어려워요");

    private final String description;

}
