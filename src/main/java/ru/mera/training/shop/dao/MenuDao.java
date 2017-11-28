package ru.mera.training.shop.dao;

import ru.mera.training.shop.entity.Menu;

import java.util.List;

public interface MenuDao extends BasicDao<Menu>{

    /**
     * method for receiving all menu with specified name
     *
     * @return List of menu
     **/
    List<Menu> getMenuByName(String name);
}
