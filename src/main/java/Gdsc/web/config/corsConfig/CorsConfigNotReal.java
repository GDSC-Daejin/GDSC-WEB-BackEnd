package Gdsc.web.config.corsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Profile("!real")
@Configuration
public class CorsConfigNotReal implements CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //  내 서버가 응답할때 json을 자바스크립트가 처리할수있게 할건지
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*"); // 모든 헤더 응답허용
        config.addAllowedMethod("*"); // 모든 post get put delete 요청 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}