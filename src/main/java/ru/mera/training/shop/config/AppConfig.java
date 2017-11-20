package ru.mera.training.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import ru.mera.training.shop.model.CreateTable;

import javax.inject.Inject;

@Configuration
@PropertySource(value = "classpath:util.properties")
@PropertySource(value = "classpath:auth.properties")
public class AppConfig {
    private final Environment environment;

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.database.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.database.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.database.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.database.password"));
        return dataSource;
    }
    @Bean
    public UserDetailsService userDetailService(){
         JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
         jdbcDao.setDataSource(dataSource());
         //jdbcDao.setUsersByUsernameQuery("SELECT login, password, true FROM users WHERE login = ?");
         //jdbcDao.setAuthoritiesByUsernameQuery("SELECT login, role_name FROM users WHERE login = ?");
         jdbcDao.setUsersByUsernameQuery(environment.getRequiredProperty("usersByQuery"));
         jdbcDao.setAuthoritiesByUsernameQuery(environment.getRequiredProperty("rolesByQuery"));
         return jdbcDao;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public CreateTable createTable() {
        return new CreateTable(jdbcTemplate());
    }
}
