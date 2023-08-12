package org.youcancook.gobong.domain.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindFolloweeResponse {
    private String profileImageURL;
    private String nickname;
    private long userId;

    public static FindFolloweeResponse of(String profileImageURL, String nickname, long userId) {
        return FindFolloweeResponse.builder()
                .profileImageURL(profileImageURL)
                .nickname(nickname)
                .userId(userId)
                .build();
    }
}
