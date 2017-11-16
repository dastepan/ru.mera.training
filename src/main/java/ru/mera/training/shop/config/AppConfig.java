package ru.mera.training.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.mera.training.shop.model.CreateTable;

@Configuration
@PropertySource(value = "classpath:util.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.postgres.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.postgres.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.postgres.name"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.postgres.password"));
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public CreateTable createTable (){
        return new CreateTable(jdbcTemplate());
    }
}
