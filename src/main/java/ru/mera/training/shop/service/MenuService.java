package ru.mera.training.shop.service;


import ru.mera.training.shop.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * method for receiving all Menu
     *
     * @return all Menu
     **/
    List<Menu> getAllMenu();

    /**
     * method for receiving Menu by id
     *
     * @param id = Menu id
     * @return Menu
     **/
    Menu getMenuById(int id);

    /**
     * method to add new Menu to DB
     *
     * @param menu   Menu that should be added to DB
     * @return Menu
     **/
    Menu addMenu(Menu menu);
    /**
     * method to update Menu in DB
     *
     * @param menu   Menu that should be updated in DB
     * @return Menu
     **/
    Menu updateMenu(Menu menu);
    /**
     * method to delete Menu By Id from DB
     *
     * @param id  Menu's id that should be deleted from DB
     * @return deleted Menu
     **/
    Menu deleteMenu(int id);
    /**
     * method to get list of Menu which have specified name
     *
     * @param name   Menu's name to search
     * @return List of Menu with same name
     **/
    List<Menu> getMenuByName(String name);
}
