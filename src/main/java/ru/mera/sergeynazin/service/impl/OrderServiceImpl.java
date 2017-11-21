package ru.mera.sergeynazin.service.impl;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.OrderService;

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
public class OrderServiceImpl implements OrderService {

    private JpaRepository orderRepository, shaurmaRepository;

    public void setOrderRepository(final JpaRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setShaurmaRepository(final JpaRepository shaurmaRepository) {
        this.shaurmaRepository = shaurmaRepository;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Order> getAll() {
        final CriteriaQuery<Order> criteriaQuery = orderRepository.myCriteriaQuery();
        final Root<Order> from = criteriaQuery.from(Order.class);
        criteriaQuery.select(from);
        return orderRepository.getByCriteriaQuery(criteriaQuery);
    }

    @Override
    public Optional<Order> getOptionalIsExist(final Long id) {
        return orderRepository.getOptionalById(id);
             // Optional.ofNullable(orderRepository.get(id));
    }

    @Override
    public Optional<Order> getOptionalIsExist(final String orderNumber) {
        final CriteriaBuilder criteriaBuilder = orderRepository.myCriteriaBuilder();
        final CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        final Root<Order> ingredientRoot = criteriaQuery.from(Order.class);

        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("order_number"), orderNumber));

        final Order nullable = orderRepository.getUniqueByCriteriaQuery(criteriaQuery);

        //TODO Temporary for testing
        if (nullable != null) {
            Hibernate.initialize(nullable.getShaurmaList());
        }

        return Optional.ofNullable(nullable);
    }

    @Override
    public Order getOrThrow(final Long id) throws NotFoundException {
        return getOptionalIsExist(id)
            .orElseThrow(() -> NotFoundException.getNew(id));
    }

    @Override
    public Order getOrThrow(String orderNumber) throws NotFoundException {
        return getOptionalIsExist(orderNumber)
            .orElseThrow(() -> NotFoundException.getNew(orderNumber));
    }

    @Transactional
    @Override
    public Long postOrThrow(final Order transient_) throws NotFoundException {
        // FIXME: 11/21/17 Check payment
        return (Long) orderRepository.create(transient_);
    }



    @Transactional
    @Override
    public Order putOrThrow(final Order newDetached) throws NotFoundException {
        throw new UnsupportedOperationException("PUT on Orders not implemented");
    }

    @Transactional
    @Override
    public Order deleteByIdOrThrow(final Long id) throws NotFoundException {
        return getOptionalIsExist(id)
            .map(shaurma -> {
                orderRepository.remove(shaurma);
                return shaurma;
            }).orElseThrow(() -> NotFoundException.getNew(id));
    }

    @Override
    public boolean validateExistsOrThrow(final Order... orders) throws NotFoundException {
        throw new UnsupportedOperationException("Validation og varargs Orders... not implemented yet");
    }

    @SafeVarargs
    private final void checkNestedCollection(final Collection<Shaurma> collection, final Predicate<? super Shaurma>... predicates)
        throws NotFoundException {
        Stream.of(predicates)
            .forEach(predicate ->
                collection
                    .parallelStream()
                    .filter(predicate)
                    .map(Shaurma::getId)
                    .map(Objects::toString)
                    .reduce((n1, n2) -> n1 +", "+ n2)
                    .ifPresent(NotFoundException::throwNew));
    }

    private boolean isNestedNotExist(final Shaurma shaurma) {
        final CriteriaBuilder criteriaBuilder = shaurmaRepository.myCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("id"), shaurma.getId()));
        return shaurmaRepository.getByCriteriaQuery(criteriaQuery).isEmpty();
    }
}
