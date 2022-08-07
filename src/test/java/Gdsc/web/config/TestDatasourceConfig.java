package Gdsc.web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@TestConfiguration
public class TestDatasourceConfig {
    @Primary
    @Bean
    public DataSource dataSource()
    {
        HikariDataSource hikariDataSource = (HikariDataSource) DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb;MODE=MySQL")
                .username("SA")
                .password("")
                .build();

        return hikariDataSource;
    }

}