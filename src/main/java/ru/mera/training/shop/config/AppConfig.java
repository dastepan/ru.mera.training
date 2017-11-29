package ru.mera.training.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import ru.mera.training.shop.dao.IngredientDao;
import ru.mera.training.shop.dao.MenuDao;
import ru.mera.training.shop.dao.OrderDao;
import ru.mera.training.shop.dao.ProductDao;
import ru.mera.training.shop.dao.dao.impl.IngredientDaoImpl;
import ru.mera.training.shop.dao.dao.impl.MenuDaoImpl;
import ru.mera.training.shop.dao.dao.impl.OrderDaoImpl;
import ru.mera.training.shop.dao.dao.impl.ProductDaoImpl;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Menu;
import ru.mera.training.shop.entity.Order;
import ru.mera.training.shop.entity.Product;
import ru.mera.training.shop.model.CreateTable;

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
    public UserDetailsService userDetailService() {
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

    @Bean
    public IngredientDao ingredientDao() {
        return new IngredientDaoImpl(Ingredient.class);
    }

    @Bean
    public MenuDao menuDao(){
        return new MenuDaoImpl(Menu.class);
    }

    @Bean
    public OrderDao orderDao(){
        return new OrderDaoImpl(Order.class);
    }

    @Bean
    public ProductDao productDao(){
        return new ProductDaoImpl(Product.class);
    }
}
