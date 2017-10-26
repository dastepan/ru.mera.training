package ru.mera.sergeynazin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.IRepository;
import ru.mera.sergeynazin.service.IngredientService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class IngredientServiceImpl implements IngredientService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        logger.info("IngredientServiceImpl::setRepository() called with: repository = [" + repository + "]");
        this.repository = repository;
    }

    @Override
    public void save(final Ingredient ingredient) {
        logger.info("IngredientServiceImpl::save() called with: ingredient = [" + ingredient + "]");
        repository.createItem(ingredient);
    }

    @Override
    public Ingredient loadAsPersistent(final Long id) {
        logger.info("IngredientServiceImpl::loadAsPersistent() called with: id = [" + id + "]");
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ingredient> getAll() {
        logger.info("IngredientServiceImpl::getAll() called");
        final CriteriaQuery<Ingredient> criteriaQuery = repository.myCriteriaQuery();
        final Root<Ingredient> root = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }

    @Override
    public void update(final Ingredient detachedEntity) {
        logger.info("IngredientServiceImpl::update() called with: detachedEntity = [" + detachedEntity + "]");
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final Ingredient persistentIngredient) {
        logger.info("IngredientServiceImpl::delete() called with: persistentIngredient = [" + persistentIngredient + "]");
        repository.deleteItem(persistentIngredient);
    }

    @Override
    public boolean tryDelete(final Long id) {
        logger.info("IngredientServiceImpl::tryDelete() called with: id = [" + id + "]");
        final Ingredient ingredient = repository.get(id);
        if (ingredient != null) {
            repository.deleteItem(ingredient);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Ingredient> optionalIsExist(final String name) {
        logger.info("IngredientServiceImpl::optionalIsExist() called with: name = [" + name + "]");
        final CriteriaBuilder criteriaBuilder = repository.getSession().getCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("name"), name));

        return Optional.of(repository.uniqueRead(criteriaQuery));
    }

    @Override
    public Optional<Ingredient> optionalIsExist(Long id) {
        logger.info("IngredientServiceImpl::optionalIsExist() called with: id = [" + id + "]");
        return Optional.of(repository.get(id));
    }
}
