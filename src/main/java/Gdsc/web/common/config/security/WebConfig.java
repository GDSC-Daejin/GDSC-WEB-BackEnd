package Gdsc.web.common.config.security;

import Gdsc.web.common.handler.AuthorityAdminHandler;
import Gdsc.web.common.handler.AuthorityNotGuestHandler;
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
    private final AuthorityAdminHandler authorityAdminHandler;
    private final AuthorityNotGuestHandler authorityNotGuestHandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 처음 접속할 때 토큰 검증 인터셉터 추가
        registry.addInterceptor(tokenValidateInterceptor)
                .addPathPatterns("/api/member/**")
                .addPathPatterns("/api/admin/**")
                .addPathPatterns("/api/core/**")
                .addPathPatterns("/api/guest/**")
                .excludePathPatterns("/refresh");
        // 이후 접속할 때 권한 검증 인터셉터 추가
        registry.addInterceptor(authorityAdminHandler)
                .addPathPatterns("/api/admin/**")
                .addPathPatterns("/api/core/**");
        registry.addInterceptor(authorityNotGuestHandler)
                .addPathPatterns("/api/member/**");
    }


}
