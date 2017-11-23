package ru.mera.sergeynazin.repository;

import java.io.Serializable;

public interface DataAccessObject {


    // CREATE
    <T> Serializable create(T transientEntity);

    // READ
    <T> T getById(Serializable id);

    // UPDATE
    <T> void update(T detachedEntity);
    <T> T mergeStateWithDbEntity(T detachedEntity);

    // DELETE
    <T> void delete(T persistentOrDetachedEntity);
    void deleteById(Serializable id);
}
