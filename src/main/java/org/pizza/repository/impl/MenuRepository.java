package org.pizza.repository.impl;

import org.pizza.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select m from Menu m where m.name = :name")
    default List<Menu> findByName(@Param("name") String name) {
        return null;
    }
}
