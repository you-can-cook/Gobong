package org.youcancook.gobong.domain.bookmarkrecipe.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookmarkType {
    TASTY("맛있어요"), CHEAP("저렴해요"), EASY("간편해요");

    private final String description;

}
