package ru.mera.sergeynazin.service.impl;

import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repos.IRepository;
import ru.mera.sergeynazin.service.IngredientService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class IngredientServiceImpl implements IngredientService {

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Ingredient ingredient) {
        repository.createItem(ingredient);
    }

    @Override
    public Ingredient loadAsPersistent(final Long id) {
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ingredient> getAll() {
        final CriteriaQuery<Ingredient> criteriaQuery = repository.myCriteriaQuery();
        final Root<Ingredient> root = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }

    @Override
    public void update(final Ingredient detachedEntity) {
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final Ingredient persistentIngredient) {
        repository.deleteItem(persistentIngredient);
    }

    @Override
    public boolean tryDelete(final Long id) {
        final Ingredient ingredient = repository.get(id);
        if (ingredient != null) {
            repository.deleteItem(ingredient);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Ingredient> optionalIsExist(final String name) {

        final CriteriaBuilder criteriaBuilder = repository.getSession().getCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("name"), name));

        return Optional.of(repository.uniqueRead(criteriaQuery));
    }

    @Override
    public Optional<Ingredient> optionalIsExist(Long id) {
        return Optional.of(repository.get(id));
    }
}
