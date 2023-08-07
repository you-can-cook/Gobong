package org.youcancook.gobong.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupDto {

    private String nickname;
    private String oAuthProvider;
    private String oAuthId;
    private String profileImageURL;
}
