package Gdsc.web;


import Gdsc.web.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*@ComponentScan({ "Gdsc.web.*"})
@ComponentScan(basePackages = {"Gdsc.web.repository.member"})
@EnableJpaRepositories(basePackages = {"Gdsc.web.repository"},
        excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = ElasticPostRepository.class))*/

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@EnableWebSecurity
public class GdscWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GdscWebApplication.class, args);
    }


}
