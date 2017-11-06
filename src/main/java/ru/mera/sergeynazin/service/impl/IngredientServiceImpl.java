package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.IngredientService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class IngredientServiceImpl implements IngredientService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void save(final Ingredient transientEntity) {
        repository.create(transientEntity);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Ingredient> getAll() {
        final CriteriaQuery<Ingredient> criteriaQuery = repository.myCriteriaQuery();
        final Root<Ingredient> root = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void update(final Ingredient detachedEntity) {
        repository.update(detachedEntity);
    }

    @Transactional
    @Override
    public void delete(final Ingredient persistentOrDetachedEntity) {
        repository.delete(persistentOrDetachedEntity);
    }


    @Override
    public Optional<Ingredient> optionalIsExist(final String name) {
        final CriteriaBuilder criteriaBuilder = repository.myCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("name"), name));

        return Optional.of(repository.getUniqueByCriteriaQuery(criteriaQuery));
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Ingredient_managed_instance)
     */
    @Override
    public Optional<Ingredient> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
            // Optional.of(repository.get(id));
    }
}
