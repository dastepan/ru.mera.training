package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.query.internal.AbstractProducedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface HibernateRepository extends JpaRepository, GenericRepository {


    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    @Override
    default <T> Serializable create(final T transientEntity) {
        return getSession().save(transientEntity);
    }

    @Override
    default <T> void delete(final T persistentOrDetachedEntity) {
        getSession().delete(persistentOrDetachedEntity);
    }

    @Override
    default <T> void update(final T detachedEntity) {
        getSession().update(detachedEntity);
    }

    @Override
    default <T> List getByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        return getSession()
            .createQuery(Objects.requireNonNull(criteriaQuery))
            .getResultList();
    }

    @Override
    default <T> T getUniqueByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        return AbstractProducedQuery.uniqueElement(
            getSession().createQuery(Objects.requireNonNull(criteriaQuery)).getResultList());
    }

    /**
     * @param newStatefulEntityWithId new Stateful Entity With ID
     * @param <T> entity type
     * @return updated managed Entity
     */
    @SuppressWarnings({"unchecked"})
    @Override
    default <T> T mergeStateWithDbEntity(final T newStatefulEntityWithId) {
        return (T) getSession().merge(newStatefulEntityWithId);
    }

    @Override
    default <T> T getById(final Serializable id) {
        return getSession().get(getClazz(), Objects.requireNonNull(id));
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Managed_instance)
     */
    @SuppressWarnings({"unchecked"})
    default <T> Optional<T> getOptionalById(final Serializable id) {
        return (Optional<T>) getSession().byId(getClazz()).loadOptional(Objects.requireNonNull(id));
        // Optional.of(getSession().get(getClazz(),Objects.requireNonNull(id)));
    }

    @Override
    default <T> CriteriaQuery<T> myCriteriaQuery() {
        return getSession().getCriteriaBuilder().createQuery(getClazz());
    }

    @Override
    default CriteriaBuilder myCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }

}
