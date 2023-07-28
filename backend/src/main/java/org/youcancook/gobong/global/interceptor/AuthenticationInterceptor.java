package org.youcancook.gobong.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.AuthenticationException;
import org.youcancook.gobong.global.util.TokenManager;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isOptionsMethod(request)) {
            return true;
        }

        String authorizationHeader = getAuthorizationValueFromRequestHeader(request);
        validTokenExistence(authorizationHeader);
        String accessToken = validGrantTypeAndGetAccessToken(authorizationHeader);

        tokenManager.validateToken(accessToken);

        return true;
    }

    private static boolean isOptionsMethod(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.name());
    }

    private static String getAuthorizationValueFromRequestHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private static void validTokenExistence(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.EMPTY_AUTHORIZATION);
        }
    }

    private static String validGrantTypeAndGetAccessToken(String authorizationHeader) {
        String[] authorizations = authorizationHeader.split(" ");
        if (authorizations.length < 2 || (!"Bearer".equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_BEARER_GRANT_TYPE);
        }
        return authorizations[1];
    }
}