package org.pizza.repository.impl;

import org.pizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza,Integer> {

    @Query("select p from Pizza p where p.name = :name")
    List<Pizza> findByName(@Param("name") String name);

}
