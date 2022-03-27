package Gdsc.web;


import Gdsc.web.config.SecurityConfig;
import Gdsc.web.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@ComponentScan({ "Gdsc.web.*"})
@ComponentScan(basePackages = {"Gdsc.web.repository.member"})
@EnableJpaRepositories(basePackages = {"Gdsc.web.repository"})
public class GdscWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GdscWebApplication.class, args);
    }


}
