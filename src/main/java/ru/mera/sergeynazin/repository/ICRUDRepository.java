package ru.mera.sergeynazin.repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ICRUDRepository {
    <T> void create(T entityWithoutPrimaryKey);
    <T> List read(CriteriaQuery<T> criteriaQuery);
    <T> void update(T entityWithPrimaryKey);
    <T> void delete(T entityWithPrimaryKey);
}
