package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.query.internal.AbstractProducedQuery;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface IRepository extends ICRUDRepository {

    Session getSession();

    @SuppressWarnings("unchecked")
    <T> Class<T> getClazz();

    @Override
    default <T> void createItem(final T item) {
        getSession().save(item);
    }


    @Override
    default <T> void deleteItem(final T item) {
        getSession().delete(item);
    }

    @Override
    default <T> void updateItem(final T item) {
        getSession().update(item);
    }

    @Override
    default <T> List readItems(final CriteriaQuery<T> criteriaQuery) {
        return getSession()
            .createQuery(Objects.requireNonNull(criteriaQuery))
            .getResultList();
    }

    default <T> void persist(final T item) {
        getSession().persist(item);
    }

    default <T> T uniqueRead(final CriteriaQuery<T> criteriaQuery) {
        return AbstractProducedQuery.uniqueElement(getSession()
            .createQuery(Objects.requireNonNull(criteriaQuery))
            .getResultList());
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Managed_instance)
     */
    @SuppressWarnings("unchecked")
    default <T> Optional<T> get(final Serializable id) {
        return (Optional<T>) getSession().byId(getClazz()).loadOptional(id);
                // get(getClazz(),Objects.requireNonNull(id));
    }

    default <T> T load(final Serializable id) {
        return getSession().load(getClazz(),Objects.requireNonNull(id));
    }

    default <T> CriteriaQuery<T> myCriteriaQuery() {
        return getSession().getCriteriaBuilder().createQuery(getClazz());
    }

}
