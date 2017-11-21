package ru.mera.sergeynazin.repository;

import java.util.Collection;
import java.util.List;

public interface Repository {

    // ADD
    <T> T add(T transientEntity);
    /**
     * Adds to the source all the elements
     * that are contained in the specified collection.
     * @param transientEntities collection containing
     * elements to be added to the source
     */
    <T> List addAll(Collection<T> transientEntities);

    // REMOVE
    <T> void remove(T persistentOrDetachedEntity);

    /**
     * Removes from the source all of its elements
     * that are contained in the specified collection.
     * @param persistentOrDetachedEntities collection
     * containing elements to be removed from the source
     */
    <T> void removeAll(Collection<T> persistentOrDetachedEntities);

    // helpers
    long count();
}
