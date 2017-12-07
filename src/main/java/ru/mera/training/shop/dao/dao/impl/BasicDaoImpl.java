package ru.mera.training.shop.dao.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.mera.training.shop.dao.BasicDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class BasicDaoImpl<T> implements BasicDao<T> {
    private final Class<T> entityClass; // сущность с которой будет работать метод

    @Autowired
    protected SessionFactory sessionFactory;

    public BasicDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> getList() {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass); // select * from entityClass

        query.select(root);
        return sessionFactory.getCurrentSession().createQuery(query).list();
    }

    @Override
    public Set<T> getSet() {
        Set<T> set = new HashSet<>();
        set.addAll(getList());
        return set;
    }

    @Override
    public T getById(int id) {
        return sessionFactory.getCurrentSession().get(entityClass, id);
    }

    @Override
    public T add(T entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public T delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
        return entity;
    }

    @Override
    public T delete(int id) {
        T entity = sessionFactory.getCurrentSession().get(entityClass, id);
        sessionFactory.getCurrentSession().delete(entity);
        return entity;
    }
}
