package Gdsc.web;


import Gdsc.web.common.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@ComponentScan({ "Gdsc.web.*"})
@EnableJpaRepositories(basePackages = {"Gdsc.web.*"})
@EnableWebSecurity
public class GdscWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GdscWebApplication.class, args);
    }


}
