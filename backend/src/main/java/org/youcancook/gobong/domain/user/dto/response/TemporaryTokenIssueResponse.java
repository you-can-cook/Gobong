package org.youcancook.gobong.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemporaryTokenIssueResponse {
    private String temporaryToken;
}
