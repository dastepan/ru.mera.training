package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see JpaRepository
 *
 * @author sergeynazin
 * */


public interface IngredientService {
    void save(Ingredient transientEntity);
    List<Ingredient> getAll();
    void update(Ingredient detachedEntity);
    Ingredient merge(Ingredient detachedEntity);
    void delete(Ingredient persistentOrDetachedEntity);
    Optional<Ingredient> optionalIsExist(String name);
    Optional<Ingredient> optionalIsExist(Long id);
}
