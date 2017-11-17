package org.pizza.service.impl;

import org.pizza.model.Menu;
import org.pizza.repository.impl.MenuRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuServiceImpl implements ServiceCommand<Menu> {
    private MenuRepository repository;

    @Override
    public void save(Menu entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Menu entity) {
        repository.delete(entity);
    }

    @Override
    public List<Menu> getAll() {
        return repository.findAll();
    }

    @Autowired
    public MenuRepository getRepository() {
        return repository;
    }
}
