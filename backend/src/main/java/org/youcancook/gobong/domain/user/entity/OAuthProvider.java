package org.youcancook.gobong.domain.user.entity;

import org.youcancook.gobong.domain.user.exception.OAuthProviderNotFoundException;

public enum OAuthProvider {
    KAKAO, GOOGLE;

    public static OAuthProvider convertToEnum(String provider) {
        try {
            return OAuthProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OAuthProviderNotFoundException();
        }
    }
}
