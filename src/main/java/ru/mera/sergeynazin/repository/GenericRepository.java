package ru.mera.sergeynazin.repository;

import javax.persistence.EntityManager;

public interface GenericRepository {

    EntityManager getEntityManager();

    @SuppressWarnings({"unchecked"})
    <T> Class<T> getClazz();
}
