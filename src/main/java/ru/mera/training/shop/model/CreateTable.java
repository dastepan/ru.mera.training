package ru.mera.training.shop.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CreateTable {
    private JdbcTemplate jdbcTemplate;

    public CreateTable() {

    }

    public CreateTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String createTableStatus() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS ingredients");
        jdbcTemplate.execute("CREATE TABLE ingredients \n" +
                "(\n" +
                "id INT NOT NULL, \n" +
                "name VARCHAR (45) NOT NULL \n" +
                "cost DECIMAL " +
                "CONSTRAINT firstkey PRIMARY KEY (id) \n" +
                ");");
        return "table created";
    }
}
