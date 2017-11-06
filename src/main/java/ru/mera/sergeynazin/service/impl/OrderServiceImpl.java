package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.OrderService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void save(final Order transientEntity) {
        repository.create(transientEntity);
    }

    @Override
    public Order loadAsPersistent(final Long id) {
        return repository.getById(id);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Order> getAll() {
        final CriteriaQuery<Order> criteriaQuery = repository.myCriteriaQuery();
        final Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void update(final Order detachedEntity) {
        repository.update(detachedEntity);
    }

    @Transactional
    @Override
    public void delete(final Order persistentOrDetachedEntity) {
        repository.delete(persistentOrDetachedEntity);
    }

    /**
     * Originally I used commented line as returning value
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment line below
     * @param id Primary Key
     * @return Optional.of(Order_managed_instance)
     */
    @Override
    public Optional<Order> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
            // Optional.of(repository.get(id));
    }

    @Override
    public Optional<Order> optionalIsExist(final String orderNumber) {
        final CriteriaBuilder criteriaBuilder = repository.myCriteriaBuilder();
        final CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        final Root<Order> ingredientRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("orderNumber"), orderNumber));

        return Optional.of(repository.getUniqueByCriteriaQuery(criteriaQuery));
    }


}
