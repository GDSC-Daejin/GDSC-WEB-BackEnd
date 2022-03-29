package Gdsc.web.config;


import org.apache.catalina.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;



@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String API_NAME = "GDSC-WEB-Backend API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "GDSC-WEB-Backend 명세서";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/**")) // 그중 /api/** 인 URL들만 필터링
                .build().apiInfo(apiInfo());
    }
    public ApiInfo apiInfo() {
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


}