package ru.mera.sergeynazin.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * Provides a convenient public API for any JPA Implementor
 * injecting this interface hides the implementation details
 * So all changes for switching to another implementor are
 * made solely in Spring and by extending/implementing this interface
 * No changes needed in other layers (Like CONTROLLERS or SERVICES)
 */
public interface JpaRepository extends Repository {

    // CREATE
    <T> Serializable create(T transientEntity);

    // UPDATE
    //<T> void update(T detachedEntity);
    <T> T mergeStateWithDbEntity(T detachedEntity);

    // READ
    <T> T getUniqueByCriteriaQuery(CriteriaQuery<T> criteriaQuery);
    <T> List getByCriteriaQuery(CriteriaQuery<T> criteriaQuery);
    <T> Optional<T> getOptionalById(Serializable id);

    // helpers
    <T> CriteriaQuery<T> myCriteriaQuery();
    CriteriaBuilder myCriteriaBuilder();

}
