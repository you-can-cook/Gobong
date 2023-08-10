package org.youcancook.gobong.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.youcancook.gobong.domain.user.dto.request.SignupRequest;

@Getter
@Builder
@AllArgsConstructor
public class SignupDto {

    private String nickname;
    private String oAuthProvider;
    private String oAuthId;
    private String profileImageURL;

    public static SignupDto from(SignupRequest signupRequest) {
        return SignupDto.builder()
                .nickname(signupRequest.getNickname())
                .oAuthProvider(signupRequest.getProvider())
                .oAuthId(signupRequest.getOauthId())
                .profileImageURL(signupRequest.getProfileImageURL())
                .build();
    }
}
