package ru.mera.sergeynazin.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.mera.sergeynazin.repository.HibernateRepository;

import javax.persistence.EntityManager;

/**
 * @author soberich
 */
@Repository
public class HibernateRepositoryImpl implements HibernateRepository {

    private SessionFactory sessionFactory;

    private Class<?> clazz;

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> HibernateRepositoryImpl(final T entity) {
        this.clazz = entity.getClass();
    }

    @Override
    public EntityManager getEntityManager() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> Class<T> getClazz() {
        return (Class<T>) clazz;
    }
}
