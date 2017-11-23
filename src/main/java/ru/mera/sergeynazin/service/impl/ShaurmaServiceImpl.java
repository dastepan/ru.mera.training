package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Transactional(readOnly = true)
public class ShaurmaServiceImpl implements ShaurmaService {

    private JpaRepository shaurmaRepository, ingredientRepository;

    public void setShaurmaRepository(final JpaRepository shaurmaRepository) {
        this.shaurmaRepository = shaurmaRepository;
    }

    public void setIngredientRepository(final JpaRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Shaurma> getAll() {
        final CriteriaQuery<Shaurma> criteriaQuery = shaurmaRepository.myCriteriaQuery();
        final Root<Shaurma> from = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(from);
        return shaurmaRepository.getByCriteriaQuery(criteriaQuery);
    }

    @Override
    public Optional<Shaurma> getOptionalIsExist(final Long id) {
        return shaurmaRepository.getOptionalById(id);
        //Optional.ofNullable(shaurmaRepository.getOptionalById(id));
    }

    @Override
    public Shaurma getOrThrow(final Long id) throws NotFoundException {
        return getOptionalIsExist(id)
            .orElseThrow(() -> NotFoundException.getNew(id));
    }

    @Transactional
    @Override
    public Shaurma postOrThrow(final Shaurma transient_) throws NotFoundException {
        checkNestedCollection(transient_.getIngredientSet(), this::isNestedNotExist);
        return shaurmaRepository.mergeStateWithDbEntity(transient_);
            //(Long) shaurmaRepository.create(transient_);
    }

    @Transactional
    @Override
    public Shaurma putOrThrow(final Shaurma newDetached) throws NotFoundException {
        checkNestedCollection(newDetached.getIngredientSet(), this::isNestedNotExist);
        return shaurmaRepository.mergeStateWithDbEntity(newDetached);
    }

    @Transactional
    @Override
    public Shaurma deleteByIdOrThrow(final Long id) throws NotFoundException {
        return getOptionalIsExist(id)
            .map(shaurma -> {
                shaurmaRepository.remove(shaurma);
                return shaurma;
            }).orElseThrow(() -> NotFoundException.getNew(id));
    }

    @Override
    public boolean validateExistsOrThrow(final Shaurma... shaurmas) throws NotFoundException {
        Stream.of(shaurmas)
            .parallel()
            .filter(shaurma -> !getOptionalIsExist(shaurma.getId()).isPresent())
            .map(shaurma -> Objects.toString(shaurma.getId(), null!=shaurma.getName()?shaurma.getName():"'no_id_or_name_specified'"))
            .reduce((n1, n2) -> n1 +", "+ n2)
            .ifPresent(NotFoundException::throwNew);
        return true;
    }

    @SafeVarargs
    private final void checkNestedCollection(final Collection<Ingredient> collection, final Predicate<? super Ingredient>... predicates)
        throws NotFoundException {
        Stream.of(predicates)
            .forEach(predicate ->
                collection
                .parallelStream()
                .filter(predicate)
                .map(Ingredient::getName)
                .reduce((n1, n2) -> n1 +", "+ n2)
                .ifPresent(NotFoundException::throwNew));
    }

    private boolean isNestedNotExist(Ingredient ingredient) {
        final CriteriaBuilder criteriaBuilder = ingredientRepository.myCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("name"), ingredient.getName()));
        return ingredientRepository.getByCriteriaQuery(criteriaQuery).isEmpty();
    }
}
