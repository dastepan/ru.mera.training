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
    default <T> void create(final T entityWithoutPrimaryKey) {
        getSession().save(entityWithoutPrimaryKey);
    }

    @Override
    default <T> void delete(final T entityWithPrimaryKey) {
        getSession().delete(entityWithPrimaryKey);
    }

    @Override
    default <T> void update(final T entityWithPrimaryKey) {
        getSession().update(entityWithPrimaryKey);
    }

    @Override
    default <T> List read(final CriteriaQuery<T> criteriaQuery) {
        return getSession()
            .createQuery(Objects.requireNonNull(criteriaQuery))
            .getResultList();
    }

    default <T> T uniqueRead(final CriteriaQuery<T> criteriaQuery) {
        return AbstractProducedQuery.uniqueElement(
            getSession().createQuery( Objects.requireNonNull(criteriaQuery) ).getResultList());
    }

    /**
     * @param newStatefulEntityWithPrimaryKey new Stateful Entity With PrimaryKey
     * @param <T> entity type
     * @return updated managed Entity
     */
    @SuppressWarnings("unchecked")
    default <T> T mergeStateWithDbEntity(T newStatefulEntityWithPrimaryKey) {
        return (T) getSession().merge(newStatefulEntityWithPrimaryKey);
    }


    default <T> void persist(final T item) {
        getSession().persist(item);
        // TODO: As I understood the getCurrentSession flushes the session, if not then getSession().flush() here
    }

    default <T> T get(final Serializable id) {
        return getSession().get(getClazz(), Objects.requireNonNull(id));
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Managed_instance)
     */
    @SuppressWarnings("unchecked")
    default <T> Optional<T> getOptional(final Serializable id) {
        return (Optional<T>) getSession().byId(getClazz()).loadOptional(Objects.requireNonNull(id));
                // Optional.of(getSession().get(getClazz(),Objects.requireNonNull(id)));
    }

    default <T> T load(final Serializable id) {
        return getSession().load(getClazz(),Objects.requireNonNull(id));
    }

    default <T> CriteriaQuery<T> myCriteriaQuery() {
        return getSession().getCriteriaBuilder().createQuery(getClazz());
    }

}
