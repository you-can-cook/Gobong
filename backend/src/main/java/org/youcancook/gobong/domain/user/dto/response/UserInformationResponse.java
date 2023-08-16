package org.youcancook.gobong.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.youcancook.gobong.domain.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
public class UserInformationResponse {
    private Long id;
    private String nickname;
    private String profileImageURL;

    @JsonProperty("oAuthProvider")
    private String oauthProvider;

    public static UserInformationResponse from(User user) {
        return UserInformationResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImageURL(user.getProfileImageURL())
                .oauthProvider(user.getOAuthProvider().name())
                .build();
    }
}
