package Gdsc.web.common.config.security;

import Gdsc.web.oauth.handler.TokenValidateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RequiredArgsConstructor
@Profile("!test") // 추후 테스트 코드에서 401 에러 구현 필요
public class WebConfig implements WebMvcConfigurer {

    private final TokenValidateInterceptor tokenValidateInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidateInterceptor)
                .addPathPatterns("/api/member/**")
                .addPathPatterns("/api/admin/**")
                .addPathPatterns("/api/core/**")
                .addPathPatterns("/api/guest/**")
                .excludePathPatterns("/refresh");
    }


}
