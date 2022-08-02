package Gdsc.web.common.config.swagger;


import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableSwagger2
@RequiredArgsConstructor
@Profile("!prod")
public class SwaggerConfig {


    private final Environment ev;
    private static String API_NAME = "GDSC-WEB-Backend API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "GDSC-WEB-Backend 명세서";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/**")) // 그중 /api/** 인 URL들만 필터링
                .build().apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    public ApiInfo apiInfo() {
        API_NAME = "GDSC-WEB-Backend API" + Arrays.toString(ev.getActiveProfiles());
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .termsOfServiceUrl("https://gdsc-dju.com/")
                .termsOfServiceUrl("https://gdsc-dju.web.app/")
                .description(API_DESCRIPTION)
                .contact(
                        new Contact(
                                "Contact Us",
                                "https://github.com/GDSC-Daejin/GDSC-WEB-BackEnd",
                                "gudcks305@gmail.com"
                        )
                )
                .build();
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}

