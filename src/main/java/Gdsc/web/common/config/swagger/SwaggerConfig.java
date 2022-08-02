
package Gdsc.web.common.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;


@Configuration
@Profile("!prod")
public class SwaggerConfig {


    private final Environment ev;
    private static String API_NAME = "GDSC-WEB-Backend API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "GDSC-WEB-Backend 명세서";

    public SwaggerConfig(Environment ev) {
        this.ev = ev;
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Blog API")
                .version("1.0.0")
                .description("Blog API 명세서 Gateway를 이용할 경우 모든 Prefix는 /blog-route/ 를 추가 해야 합니다." +
                        "development 모드에서는 /blog-route-dev/");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}


