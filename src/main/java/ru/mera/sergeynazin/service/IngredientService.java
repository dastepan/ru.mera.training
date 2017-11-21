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

    // GET
    List<Ingredient> getAll();
    Optional<Ingredient> getOptionalIsExist(String name);
    Optional<Ingredient> getOptionalIsExist(Long id);
    Ingredient getOrThrow(Long id);

    // POST

    /**
     * @param transient_ stateful entity
     * @return new Statefull eagerly loaded entity
     * @apiNote Switched to return walue of the Entity type
     *          for convenience
     */
    Ingredient postOrThrow(Ingredient transient_);

    // PUT
    Ingredient putOrThrow(Ingredient newDetached);

    // DELETE
    Ingredient deleteByIdOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Ingredient... ingredients);
}
