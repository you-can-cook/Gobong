package org.youcancook.gobong.domain.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindFollowerResponse {
    private long userId;
    private String nickname;
    private String profileImageURL;

    public static FindFollowerResponse of(long userId, String nickname, String profileImageURL) {
        return FindFollowerResponse.builder()
                .userId(userId)
                .nickname(nickname)
                .profileImageURL(profileImageURL)
                .build();
    }
}
