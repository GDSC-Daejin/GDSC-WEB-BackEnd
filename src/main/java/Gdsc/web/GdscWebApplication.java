package Gdsc.web;


import Gdsc.web.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class GdscWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GdscWebApplication.class, args);
    }

}
