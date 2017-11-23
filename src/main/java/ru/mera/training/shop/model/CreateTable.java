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
        jdbcTemplate.execute("DROP TABLE IF EXISTS public.ingredients");

        jdbcTemplate.execute("CREATE TABLE public.ingredients\n" +
                "(\n" +
                "    id serial NOT NULL,\n" +
                "    name character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    cost integer NOT NULL,\n" +
                "    CONSTRAINT ingredients_pkey PRIMARY KEY (id)\n" +
                ")");
        return "table created";
    }
}
