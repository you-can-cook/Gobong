package org.youcancook.gobong.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.youcancook.gobong.global.interceptor.AuthenticationInterceptor;
import org.youcancook.gobong.global.resolver.UserIdArgumentResolver;
import org.youcancook.gobong.global.util.TokenManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;
    private final UserIdArgumentResolver userEmailArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(tokenManager))
                .excludePathPatterns("/api/users/login", "/api/users/signup", "/css/**", "/*.ico", "/error", "/js/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userEmailArgumentResolver);
    }
}