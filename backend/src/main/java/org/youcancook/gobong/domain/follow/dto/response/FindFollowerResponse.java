package org.youcancook.gobong.domain.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindFollowerResponse {
    private String profileImageURL;
    private String nickname;
    private long userId;

    public static FindFollowerResponse of(String profileImageURL, String nickname, long userId) {
        return FindFollowerResponse.builder()
                .profileImageURL(profileImageURL)
                .nickname(nickname)
                .userId(userId)
                .build();
    }
}
