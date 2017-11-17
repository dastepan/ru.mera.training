package org.pizza.service.impl;

import org.pizza.model.Ingredient;
import org.pizza.repository.impl.IngredientRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IngredientServiceImpl implements ServiceCommand<Ingredient> {
    private IngredientRepository repository;

    @Override
    public void save(Ingredient entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Ingredient entity) {
        repository.delete(entity);
    }

    @Override
    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    public List<Ingredient> getByName(String name) {
        return repository.findByName(name);
    }


    @Autowired
    public IngredientRepository getRepository() {
        return repository;
    }

}
