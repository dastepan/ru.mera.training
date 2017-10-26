package ru.mera.sergeynazin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.repository.IRepository;
import ru.mera.sergeynazin.service.OrderService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    /**
     * Test
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        logger.info("OrderServiceImpl::setRepository() called with: repository = [" + repository + "]");
        this.repository = repository;
    }

    @Override
    public void save(final Order order) {
        logger.info("OrderServiceImpl::save() called with: order = [" + order + "]");
        repository.createItem(order);
    }

    @Override
    public Order loadAsPersistent(final Long id) {
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getAll() {
        logger.info("OrderServiceImpl::getAll() called");
        CriteriaQuery<Order> criteriaQuery = repository.myCriteriaQuery();
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }


    @Override
    public void update(final Order detachedEntity) {
        logger.info("OrderServiceImpl::update() called with: detachedEntity = [" + detachedEntity + "]");
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final Order persistentOrder) {
        logger.info("OrderServiceImpl::delete() called with: persistentOrder = [" + persistentOrder + "]");
        repository.deleteItem(persistentOrder);
    }

    @Override
    public boolean tryDelete(final Long id) {
        logger.info("OrderServiceImpl::tryDelete() called with: id = [" + id + "]");
        final Order order = repository.get(id);
        if (order != null) {
            repository.deleteItem(order);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Order> optionalIsExist(final Long id) {
        logger.info("OrderServiceImpl::optionalIsExist() called with: id = [" + id + "]");
        return Optional.of(repository.get(id));
    }

    @Override
    public Optional<Order> optionalIsExist(final String orderNumber) {
        logger.info("OrderServiceImpl::optionalIsExist() called with: orderNumber = [" + orderNumber + "]");
        final CriteriaBuilder criteriaBuilder = repository.getSession().getCriteriaBuilder();
        final CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        final Root<Order> ingredientRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("orderNumber"), orderNumber));

        return Optional.of(repository.uniqueRead(criteriaQuery));
    }
}
