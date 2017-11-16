package org.pizza.service;

import org.pizza.model.Menu;

import java.util.List;

public interface MenuService {
    void save(Menu entity);

    void update(Menu entity);

    void delete(Menu entity);

    List<Menu> getAll();
}
