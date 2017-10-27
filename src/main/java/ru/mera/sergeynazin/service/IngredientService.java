package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.IRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see IRepository
 *
 * @author sergeynazin
 * */


public interface IngredientService {
    void save(Ingredient ingredient);
    List<Ingredient> getAll();
    void update(Ingredient detachedEntity);
    void delete(Ingredient persistentEntity);
    Optional<Ingredient> optionalIsExist(String name);
    Optional<Ingredient> optionalIsExist(Long id);
}
