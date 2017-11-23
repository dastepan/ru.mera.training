package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.query.internal.AbstractProducedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface HibernateRepository extends JpaRepository, GenericRepository {


    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    @Override
    default <T> Serializable create(final T transientEntity) {
        Objects.requireNonNull(transientEntity, "Tried to save NULL entity");
        return getSession().save(transientEntity);
    }

    /**
     * @param newStatefulEntityWithId new Stateful Entity With ID
     * @param <T> entity type
     * @return updated managed Entity
     */
    @SuppressWarnings({"unchecked"})
    @Override
    default <T> T mergeStateWithDbEntity(final T newStatefulEntityWithId) {
        Objects.requireNonNull(newStatefulEntityWithId, "Tried to merge NULL entity");
        return (T) getSession().merge(newStatefulEntityWithId);
    }

    @Override
    default <T> T add(final T transientEntity) {
        Objects.requireNonNull(transientEntity, "Tried to add NULL entity");
        return (T) mergeStateWithDbEntity(transientEntity);
    }

    @Override
    default <T> List addAll(final Collection<T> transientEntities) {
        Objects.requireNonNull(transientEntities, "Tried to add NULL collection");
        return transientEntities.parallelStream()
            .map(this::add)
            .collect(Collectors.toList());
    }

    @Override
    default <T> void remove(final T persistentOrDetachedEntity) {
        Objects.requireNonNull(persistentOrDetachedEntity, "Tried to remove NULL entity");
        getSession().delete(persistentOrDetachedEntity);
    }

    @Override
    default <T> void removeAll(final Collection<T> persistentOrDetachedEntities) {
        Objects.requireNonNull(persistentOrDetachedEntities, "Tried to delete NULL collection");
        persistentOrDetachedEntities.parallelStream()
            .forEach(this::remove);
    }

    @Override
    default <T> List getByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        Objects.requireNonNull(criteriaQuery, "NULL Criteria passed as parameter");
        return getSession()
            .createQuery(criteriaQuery)
            .getResultList();
    }

    @Override
    default <T> T getUniqueByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        Objects.requireNonNull(criteriaQuery, "NULL Criteria passed as parameter");
        return AbstractProducedQuery.uniqueElement(
            getSession().createQuery(criteriaQuery).getResultList());
    }

    @Override
    default long count() {
        final CriteriaBuilder qb = myCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(getClazz())));
        //cq.where(/*could be ant condition*/);
        return getUniqueByCriteriaQuery(cq);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Managed_instance)
     */
    @SuppressWarnings({"unchecked"})
    @Override
    default <T> Optional<T> getOptionalById(final Serializable id) {
        Objects.requireNonNull(id, "NULL identifier passed as parameter");
        return (Optional<T>) getSession().byId(getClazz()).loadOptional(id);
        // Optional.ofNullable(getSession().get(getClazz(),Objects.requireNonNull(id)));
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
