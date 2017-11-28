package ru.mera.training.shop.dao.dao.impl;

import ru.mera.training.shop.dao.MenuDao;
import ru.mera.training.shop.entity.Menu;

import java.util.List;

public class MenuDaoImpl extends BasicDaoImpl<Menu> implements MenuDao {

    public MenuDaoImpl(Class<Menu> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Menu> getMenuByName(String name) {
        return (List<Menu>) sessionFactory.getCurrentSession()
                .createQuery("from Menu as i where i.name = ?").setParameter(0, name).list()
                ;
    }
}
