package ru.mera.sergeynazin.repository.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.repository.HibernateRepository;

import javax.persistence.EntityManager;

/**
 * @author soberich
 */

public class HibernateRepositoryImpl implements HibernateRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory;

    private Class<?> clazz;


    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO: 10/20/17 Annotations more suitable when injecting GENERICS if needed (...I guess)
    /**
     * for educational purposes MAY set through constructor but then extending would become non-trivial
     */
    public <T> void setClazz(final T entity) {
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
