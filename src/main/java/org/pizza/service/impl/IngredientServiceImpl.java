package org.pizza.service.impl;

import org.pizza.model.Ingredient;
import org.pizza.repository.impl.IngredientRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IngredientServiceImpl implements ServiceCommand<Ingredient> {
    @Autowired
    private IngredientRepository repository;//внизу Autowire над getter

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
    @Override
    public void add(Ingredient entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void remove(int id){
        repository.delete(repository.getOne(id));
    }
    @Override
    public Ingredient getById(int id){
        return repository.getOne(id);
    }

    public List<Ingredient> getByName(String name) {
        return repository.findByName(name);
    }


}
