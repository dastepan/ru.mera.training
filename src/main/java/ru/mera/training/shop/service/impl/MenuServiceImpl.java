package ru.mera.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.training.shop.dao.MenuDao;
import ru.mera.training.shop.entity.Menu;
import ru.mera.training.shop.service.MenuService;

import java.util.List;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getAllMenu() {
        return menuDao.getList();
    }

    @Override
    public Menu getMenuById(int id) {
        return menuDao.getById(id);
    }

    @Override
    public Menu addMenu(Menu menu) {
        return menuDao.add(menu);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        return menuDao.update(menu);
    }

    @Override
    public Menu deleteMenu(int id) {
        return menuDao.delete(getMenuById(id));
    }

    @Override
    public List<Menu> getMenuByName(String name) {
        return menuDao.getMenuByName(name);
    }
}
