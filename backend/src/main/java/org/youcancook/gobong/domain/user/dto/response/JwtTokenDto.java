package org.youcancook.gobong.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class JwtTokenDto {

    private String grantType;

    private String accessToken;
}
