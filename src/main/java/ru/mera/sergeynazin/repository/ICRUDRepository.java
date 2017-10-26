package ru.mera.sergeynazin.repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ICRUDRepository {
    <T> void createItem(T item);
    <T> List readItems(CriteriaQuery<T> criteriaQuery);
    <T> void updateItem(T item);
    <T> void deleteItem(T item);
}
